package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.dto.request.PlasticPickUpRequest;
import com.recycleBusiness.RecyclePal.dto.response.PlasticPickUpResponse;
import com.recycleBusiness.RecyclePal.exception.WasteNotCreated;

public interface WasteCollectionRequestServices {
    PlasticPickUpResponse createRequestDetails(PlasticPickUpRequest requestDto) throws WasteNotCreated;

    PlasticPickUpResponse updateRequestDetails(PlasticPickUpRequest requestDto);

}
