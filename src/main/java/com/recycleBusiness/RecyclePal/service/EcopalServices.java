package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.dto.request.EcopalLoginRequest;
import com.recycleBusiness.RecyclePal.dto.request.EcopalRegistrationRequest;
import com.recycleBusiness.RecyclePal.dto.request.EcopalSubmitRequest;
import com.recycleBusiness.RecyclePal.dto.request.UpdateEcopalRequest;
import com.recycleBusiness.RecyclePal.dto.response.EcopalLoginResponse;
import com.recycleBusiness.RecyclePal.dto.response.EcopalRegistrationResponse;
import com.recycleBusiness.RecyclePal.dto.response.EcopalSubmitResponse;
import com.recycleBusiness.RecyclePal.dto.response.EcopalUpdateResponse;
import com.recycleBusiness.RecyclePal.exception.*;

public interface EcopalServices {
    EcopalRegistrationResponse registration(EcopalRegistrationRequest registrationRequest) throws EcopalWithEmailOrUsernameExist, EcopalNotSaveIntoDataBase, EcopalAlreadyExistException;
    EcopalLoginResponse login(EcopalLoginRequest ecopalLoginRequest) throws RecycleException, UsernameNotFoundException;
    EcopalUpdateResponse updateProfile(UpdateEcopalRequest request) throws EcopalWithEmailOrUsernameExist, UsernameNotFoundException;

    EcopalSubmitResponse submitRequest(EcopalSubmitRequest request) throws WasteNotCreated;




}
