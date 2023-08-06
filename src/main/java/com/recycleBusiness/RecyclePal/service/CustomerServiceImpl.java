package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.data.models.Address;
import com.recycleBusiness.RecyclePal.data.models.Customer;
import com.recycleBusiness.RecyclePal.data.repository.AddressRepository;
import com.recycleBusiness.RecyclePal.data.repository.CustomerRepository;
import com.recycleBusiness.RecyclePal.dto.request.*;
import com.recycleBusiness.RecyclePal.dto.responce.CustomerLoginResponse;
import com.recycleBusiness.RecyclePal.dto.responce.CustomerRegistrationResponse;
import com.recycleBusiness.RecyclePal.dto.responce.CustomerSubmitResponse;
import com.recycleBusiness.RecyclePal.dto.responce.CustomerUpdateResponse;
import com.recycleBusiness.RecyclePal.exception.CustomerNotSaveIntoDataBase;
import com.recycleBusiness.RecyclePal.exception.CustomerWithEmailOrUsernameExist;
import com.recycleBusiness.RecyclePal.exception.UsernameNotFoundException;
import com.recycleBusiness.RecyclePal.exception.WasteNotCreated;
import com.recycleBusiness.RecyclePal.service.mail.SendMailImpl;
import com.recycleBusiness.RecyclePal.utils.AppUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.recycleBusiness.RecyclePal.utils.AppUtils.*;
import static com.recycleBusiness.RecyclePal.utils.ResponseMessage.USER_REGISTRATION_SUCCESSFUL;
import static com.recycleBusiness.RecyclePal.utils.ResponseMessage.WASTE_COLLECTION_IS_SUCCESSFULLY_CREATED;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl  implements  CustomerServices{
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final SendMailImpl  sendMail;
    private final WasteCollectionRequestServices wasteCollectionServices;
    private final AddressRepository addressRepository;
    @Override
    public CustomerRegistrationResponse customerRegistration(CustomerRegistrationRequest registrationRequest) throws CustomerWithEmailOrUsernameExist, CustomerNotSaveIntoDataBase {
        Customer customer = modelMapper.map(registrationRequest,Customer.class);

        boolean isPresent = validateUsernameAndPassword(customer.getUsername(),customer.getEmail());
        Address address = modelMapper.map(registrationRequest.getAddress(), Address.class);

        Address savedAddress = addressRepository.save(address);
        customer.setAddress(savedAddress);
		if (isPresent)
            throw new CustomerWithEmailOrUsernameExist(String.format(USERNAME_OR_PASSWORD_NOT_VALID,customer.getEmail(),customer.getUsername()));
        String password = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(password);
        Customer cus = customerRepository.save(customer);
        boolean saveCustomer = cus.getEmail() != null;
        EmailNotificationRequest notificationRequest = buildEmailRequest(customer);
        sendMail.sendMail(notificationRequest);
        if (!saveCustomer)
            throw  new CustomerNotSaveIntoDataBase(String.format(USER_NOT_SAVE_iNTO_DB,customer.getUsername()));
        return buildResponse(customer);
    }

    @Override
    public CustomerLoginResponse login(CustomerLoginRequest LoginRequest) {
        return null;
    }

    @Override
    @Transactional
    public CustomerUpdateResponse updateProfile(UpdateCustomerRequest request) throws CustomerWithEmailOrUsernameExist, UsernameNotFoundException {

        Customer updateCustomer=
                customerRepository.findCustomerByUsername(request.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND,request.getUsername())));
        if (request.getFirstname() != null)
            updateCustomer.setFirstname(request.getFirstname());

        if (request.getLastname() != null)
            updateCustomer.setLastname(request.getLastname());

        if (request.getPhoneNumber() != null)
            updateCustomer.setPassword(request.getPhoneNumber());

         customerRepository.save(updateCustomer);
        return buildUpdateResponse(updateCustomer);
    }

    public CustomerSubmitResponse submitRequest(CustomerSubmitRequest request) throws WasteNotCreated {
        Customer customer = modelMapper.map(request, Customer.class);
        WasteCollectionRequestDto wasteCollection = wasteCollectionBuild(request);
        customer.setAddress(wasteCollection.getAddress());
        wasteCollection.setRequesterId(customer.getId());
        wasteCollectionServices.createRequestDetails(wasteCollection);
        return CustomerSubmitResponse.builder().message(WASTE_COLLECTION_IS_SUCCESSFULLY_CREATED).build();
    }
    private WasteCollectionRequestDto wasteCollectionBuild(CustomerSubmitRequest request) {
        return WasteCollectionRequestDto.builder()
                .createdTime(request.getCreatedTime())
                .pickedUptime(request.getPickedUptime())
                .quantity(request.getQuantity())
                .build();
    }

    private CustomerUpdateResponse buildUpdateResponse(Customer customer){
        return CustomerUpdateResponse.builder()
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

    private boolean validateUsernameAndPassword(String username, String email){
        boolean checkForUsername = customerRepository.findCustomerByUsername(username).isPresent();
        boolean checkForUserEmail = customerRepository.findByEmail(email).isPresent();
        return checkForUsername || checkForUserEmail;
    }

    private CustomerRegistrationResponse buildResponse(Customer customer){
        return CustomerRegistrationResponse.builder()
                .message(USER_REGISTRATION_SUCCESSFUL)
                .username(customer.getUsername())
                .build();
    }

    private EmailNotificationRequest buildEmailRequest(Customer customer){
//        String token = JWT.create()
//                .withIssuedAt(Instant.now())
//               .withExpiresAt(Instant.now().plusSeconds(900 ))
//                .sign(Algorithm.HMAC384("secret".getBytes()));

        EmailNotificationRequest request = new EmailNotificationRequest();
        Sender sender = new Sender("RecyclePal", "test@email.com");
        Recipient recipient = new Recipient(customer.getFirstname(),customer.getEmail());
        request.setEmailSender(sender);
        request.setRecipients(Set.of(recipient));
        request.setSubject("ACTIVATION_LINK_VALUE");
        String template = AppUtils.buildWelcomeEmail(customer.getFirstname(), COMPANY_NAME, COMPANY_NAME);
        request.setSetContent(template);
        return request;
    }
    public void deleteAll(){
        customerRepository.deleteAll();
    }

}
