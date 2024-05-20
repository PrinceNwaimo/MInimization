package com.recycleBusiness.RecyclePal.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.recycleBusiness.RecyclePal.data.models.Address;
import com.recycleBusiness.RecyclePal.data.models.Agent;
import com.recycleBusiness.RecyclePal.data.models.Ecopal;
import com.recycleBusiness.RecyclePal.data.repository.AddressRepository;
import com.recycleBusiness.RecyclePal.data.repository.EcopalRepository;
import com.recycleBusiness.RecyclePal.dto.request.*;
import com.recycleBusiness.RecyclePal.dto.response.*;
import com.recycleBusiness.RecyclePal.exception.*;
import com.recycleBusiness.RecyclePal.service.mail.SendMailImpl;
import com.recycleBusiness.RecyclePal.utils.AppUtils;
import com.recycleBusiness.RecyclePal.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.recycleBusiness.RecyclePal.utils.AppUtils.*;
import static com.recycleBusiness.RecyclePal.utils.ExceptionUtils.ACCOUNT_VERIFICATION_FAILED;
import static com.recycleBusiness.RecyclePal.utils.ExceptionUtils.USER_WITH_ID_NOT_FOUND;
import static com.recycleBusiness.RecyclePal.utils.ResponseMessage.USER_REGISTRATION_SUCCESSFUL;
import static com.recycleBusiness.RecyclePal.utils.ResponseMessage.WASTE_COLLECTION_IS_SUCCESSFULLY_CREATED;
import static com.recycleBusiness.RecyclePal.utils.ResponseUtils.*;

@Service
@Slf4j
@AllArgsConstructor
public class EcopalServiceImplementation implements EcopalServices {
    private final EcopalRepository ecopalRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final SendMailImpl  sendMail;
    private final JwtUtils jwtUtils;
    private final WasteCollectionRequestServices wasteCollectionServices;

    private final AddressRepository addressRepository;
    @Override
    public EcopalRegistrationResponse registration(EcopalRegistrationRequest registrationRequest) throws EcopalAlreadyExistException {
        Optional<Ecopal> findEcoPal = ecopalRepository.findByEmail(registrationRequest.getEmail());
		if (findEcoPal.isPresent())
            throw new EcopalAlreadyExistException(String.format(ECOPAL_ALREADY_EXIST, registrationRequest.getEmail()));
        String password = passwordEncoder.encode(registrationRequest.getPassword());
        registrationRequest.setPassword(password);
        Address address = modelMapper.map(registrationRequest, Address.class);
        Ecopal ecopal = modelMapper.map(registrationRequest, Ecopal.class);
        ecopal.setAddress(address);
        Ecopal savedEcoPal = ecopalRepository.save(ecopal);
        buildEmailRequest(registrationRequest);
        return buildResponse(savedEcoPal);
    }

    @Override
    public EcopalLoginResponse login(EcopalLoginRequest ecopalLoginRequest) throws RecycleException,UsernameNotFoundException {
       var foundEcopal = ecopalRepository.findByEmail(ecopalLoginRequest.getEmail()).orElseThrow(()->new AgentNotFoundException("This is not a registered user, kindly register"));
       verifyEcopal(foundEcopal.getEmail());
       if(!passwordEncoder.matches(ecopalLoginRequest.getPassword(), foundEcopal.getPassword())){
           throw new InvalidDetailsException("Wrong Login details");
       }
       return buildLoginResponse(foundEcopal.getId());


    }
    public ApiResponse<?> verifyEcopal(String token) throws RecycleException, UsernameNotFoundException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtUtils.getSecret().getBytes()))
                .build().verify(token);
        if (decodedJWT == null) throw new RecycleException(ACCOUNT_VERIFICATION_FAILED);
        Claim claim = decodedJWT.getClaim(ID);
        Long id = claim.asLong();
        Ecopal foundUser = ecopalRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_WITH_ID_NOT_FOUND, id)));
        foundUser.setIsEnabled(true);
        ecopalRepository.save(foundUser);
        return ApiResponse.builder()
                .message(ACCOUNT_VERIFIED_SUCCESSFULLY)
                .build();

    }



    private EcopalLoginResponse buildLoginResponse(Long id) {
        EcopalLoginResponse ecopalLoginResponse = new EcopalLoginResponse();
        ecopalLoginResponse.setId(id);
        ecopalLoginResponse.setMessage(USER_LOGIN_SUCCESSFUL);
        return  ecopalLoginResponse;
    }

    @Override
    public EcopalUpdateResponse updateProfile(UpdateEcopalRequest request) throws EcopalWithEmailOrUsernameExist, UsernameNotFoundException {

        Ecopal updateCustomer=
                ecopalRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException(String.format(EMAIL_NOT_FOUND,request.getEmail())));
        if (request.getFirstname() != null)
            updateCustomer.setFirstname(request.getFirstname());

        if (request.getLastname() != null)
            updateCustomer.setLastname(request.getLastname());

        if (request.getPhoneNumber() != null)
            updateCustomer.setPassword(request.getPhoneNumber());

         ecopalRepository.save(updateCustomer);
        return buildUpdateResponse(updateCustomer);
    }

    public EcopalSubmitResponse submitRequest(EcopalSubmitRequest request) throws WasteNotCreated {
        Ecopal customer = modelMapper.map(request, Ecopal.class);
        PlasticPickUpRequest wasteCollection = wasteCollectionBuild(request);
        customer.setAddress(wasteCollection.getAddress());
        wasteCollectionServices.createRequestDetails(wasteCollection);
        return EcopalSubmitResponse.builder().message(WASTE_COLLECTION_IS_SUCCESSFULLY_CREATED).build();
    }
    private PlasticPickUpRequest wasteCollectionBuild(EcopalSubmitRequest request) {
        return PlasticPickUpRequest.builder()
                .createdTime(request.getCreatedTime())
                .pickedUptime(request.getPickedUptime())
                .quantity(request.getQuantity())
                .build();
    }

    private EcopalUpdateResponse buildUpdateResponse(Ecopal customer){
        return EcopalUpdateResponse.builder()
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

    private EcopalRegistrationResponse buildResponse(Ecopal customer){
        return EcopalRegistrationResponse.builder()
                .message(USER_REGISTRATION_SUCCESSFUL)
                .build();
    }

    private SendMailRequest buildEmailRequest(EcopalRegistrationRequest ecopalRegistrationRequest){
        SendMailRequest request = new SendMailRequest();
        request.setEmailSender(SENDER);
        request.setRecipients(RECIPIENT);
        request.setSubject(SUBJECT);
        String template = AppUtils.buildWelcomeEmail(ecopalRegistrationRequest.getEmail(), COMPANY_NAME, ACTIVATION_LINK);
        request.setSetContent(template);
        sendMail.sendMail(request);
        return request;
    }
    public void deleteAll(){
        ecopalRepository.deleteAll();
    }

}
