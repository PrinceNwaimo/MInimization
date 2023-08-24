package com.recycleBusiness.RecyclePal.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.recycleBusiness.RecyclePal.data.models.Admin;
import com.recycleBusiness.RecyclePal.data.repository.AdminRepository;
import com.recycleBusiness.RecyclePal.dto.request.AdminLoginRequest;
import com.recycleBusiness.RecyclePal.dto.request.AdminRegistrationRequest;
import com.recycleBusiness.RecyclePal.dto.response.*;
import com.recycleBusiness.RecyclePal.exception.*;
import com.recycleBusiness.RecyclePal.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.recycleBusiness.RecyclePal.utils.AppUtils.*;
import static com.recycleBusiness.RecyclePal.utils.ExceptionUtils.*;
import static com.recycleBusiness.RecyclePal.utils.ResponseUtils.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@AllArgsConstructor
@Slf4j
public class AdminServiceImplementation implements AdminService{

    private final AdminRepository adminRepository;

    private final ModelMapper modelMapper;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;


    @Override
    public AdminRegistrationResponse register(AdminRegistrationRequest adminRegistrationRequest) throws AdminRegistrationFailedException {
        Admin admin = modelMapper.map(adminRegistrationRequest, Admin.class);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        if (adminExists(adminRegistrationRequest.getEmail())) throw new IllegalArgumentException(adminRegistrationRequest.getEmail() + "already exist");
        adminRepository.save(admin);
        return buildRegisterAdminResponse(admin.getAdminId());
    }
    private boolean adminExists(String email){
        Optional<Admin> found = adminRepository.findByEmail(email);
        return found.isPresent();
    }
    private static AdminRegistrationResponse buildRegisterAdminResponse(Long adminId) {
        AdminRegistrationResponse adminRegistrationResponse = new AdminRegistrationResponse();
        adminRegistrationResponse.setMessage(ADMIN_REGISTRATION_SUCCESSFUL);
        adminRegistrationResponse.setId(adminId);

        return adminRegistrationResponse;
    }

    @Override
    public AdminLoginResponse login(AdminLoginRequest adminLoginRequest) throws RecycleException, UsernameNotFoundException {
        var foundAdmin =adminRepository.findByEmail(adminLoginRequest.getEmail()).orElseThrow(()->new AgentNotFoundException("This is not a registered Admin, Kindly Register"))  ;
        verifyAdmin(foundAdmin.getEmail());
        if(!passwordEncoder.matches(adminLoginRequest.getPassword(),foundAdmin.getPassword())) {
            throw new InvalidDetailsException("Wrong login details, Kindly try again with the right information");
        }
        return buildLoginAdminResponse(foundAdmin.getAdminId());
    }
    private static AdminLoginResponse buildLoginAdminResponse(Long adminId){
        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();
        adminLoginResponse.setMessage(USER_LOGIN_SUCCESSFUL);
        adminLoginResponse.setId(adminId);

        return adminLoginResponse;
    }
    @Override
    public ApiResponse<?> verifyAdmin(String token) throws RecycleException, UsernameNotFoundException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtUtils.getSecret().getBytes()))
                .build().verify(token);
        if (decodedJWT == null) throw new RecycleException(ACCOUNT_VERIFICATION_FAILED);
        Claim claim = decodedJWT.getClaim(ID);
        Long id = claim.asLong();
        Admin foundAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_WITH_ID_NOT_FOUND, id)));
        foundAdmin.setIsEnabled(true);
        adminRepository.save(foundAdmin);
        return ApiResponse.builder()
                .message(ACCOUNT_VERIFIED_SUCCESSFULLY)
                .build();

    }


    @Override
    public List<AdminResponse> getAllAdmins(int page, int items) {
        Pageable pageable = buildPageRequest(page, items);
        Page<Admin> adminPage = adminRepository.findAll(pageable);
        List<Admin> agents = adminPage.getContent();
        return agents.stream()
                .map(AdminServiceImplementation::buildAdminResponse)
                .toList();
    }
    private static AdminResponse buildAdminResponse(Admin admin) {
        return AdminResponse.builder()
                .id(admin.getAdminId())
                .email(admin.getEmail())
                .name(admin.getFirstName() + EMPTY_SPACE_VALUE + admin.getLastName())
                .build();
    }

    @Override
    public ApiResponse<?> deleteAdmin(Long Id) {
        adminRepository.deleteById(Id);
        return ApiResponse.builder()
                .message(USER_DELETED_SUCCESSFULLY)
                .build();
    }

    @Override
    public AdminResponse getAdminById(Long Id) throws RecycleException, UsernameNotFoundException {
        Optional<Admin> foundAdmin = adminRepository.findById(Id);
        Admin admin = foundAdmin.orElseThrow(() -> new UsernameNotFoundException(
                String.format(USER_WITH_ID_NOT_FOUND, id)
        ));
        AdminResponse adminResponse = buildAdminResponse(admin);
        return adminResponse;
    }

    @Override
    public AdminResponse getAdminByEmail(String email) throws RecycleException, UsernameNotFoundException {
        Optional<Admin> foundAdmin = adminRepository.findByEmail(email);
        Admin admin = foundAdmin.orElseThrow(() -> new UsernameNotFoundException(
                String.format(USER_WITH_EMAIL_NOT_FOUND,Email)));
        AdminResponse adminResponse = buildAdminResponse(admin);
        return adminResponse;
    }
}
