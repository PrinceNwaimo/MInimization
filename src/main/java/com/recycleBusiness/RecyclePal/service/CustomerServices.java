package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.dto.request.CustomerLoginRequest;
import com.recycleBusiness.RecyclePal.dto.request.CustomerRegistrationRequest;
import com.recycleBusiness.RecyclePal.dto.request.CustomerSubmitRequest;
import com.recycleBusiness.RecyclePal.dto.request.UpdateCustomerRequest;
import com.recycleBusiness.RecyclePal.dto.responce.CustomerLoginResponse;
import com.recycleBusiness.RecyclePal.dto.responce.CustomerRegistrationResponse;
import com.recycleBusiness.RecyclePal.dto.responce.CustomerSubmitResponse;
import com.recycleBusiness.RecyclePal.dto.responce.CustomerUpdateResponse;
import com.recycleBusiness.RecyclePal.exception.CustomerNotSaveIntoDataBase;
import com.recycleBusiness.RecyclePal.exception.CustomerWithEmailOrUsernameExist;
import com.recycleBusiness.RecyclePal.exception.UsernameNotFoundException;
import com.recycleBusiness.RecyclePal.exception.WasteNotCreated;

public interface CustomerServices {
    CustomerRegistrationResponse customerRegistration(CustomerRegistrationRequest registrationRequest) throws CustomerWithEmailOrUsernameExist, CustomerNotSaveIntoDataBase;
    CustomerLoginResponse login(CustomerLoginRequest LoginRequest);
    CustomerUpdateResponse updateProfile(UpdateCustomerRequest request) throws CustomerWithEmailOrUsernameExist, UsernameNotFoundException;

    CustomerSubmitResponse submitRequest(CustomerSubmitRequest request) throws WasteNotCreated;




}
