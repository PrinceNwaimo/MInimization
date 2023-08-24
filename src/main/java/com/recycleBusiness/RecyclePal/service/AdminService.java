package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.dto.request.AdminLoginRequest;
import com.recycleBusiness.RecyclePal.dto.request.AdminRegistrationRequest;
import com.recycleBusiness.RecyclePal.dto.response.AdminLoginResponse;
import com.recycleBusiness.RecyclePal.dto.response.AdminRegistrationResponse;
import com.recycleBusiness.RecyclePal.dto.response.AdminResponse;
import com.recycleBusiness.RecyclePal.dto.response.ApiResponse;
import com.recycleBusiness.RecyclePal.exception.*;

import java.util.List;

public interface AdminService {

    AdminRegistrationResponse register (AdminRegistrationRequest adminRegistrationRequest) throws AdminRegistrationFailedException;

    AdminLoginResponse login(AdminLoginRequest adminLoginRequest) throws RecycleException, UsernameNotFoundException;

    ApiResponse<?> verifyAdmin (String token)throws RecycleException, UsernameNotFoundException;

    List<AdminResponse> getAllAdmins(int page, int items);

    ApiResponse<?> deleteAdmin(Long Id);

    AdminResponse getAdminById(Long Id) throws RecycleException, UsernameNotFoundException;

    AdminResponse getAdminByEmail(String email) throws RecycleException, UsernameNotFoundException;
}
