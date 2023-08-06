package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.dto.request.WasteCollectionRequestDto;
import com.recycleBusiness.RecyclePal.dto.responce.WasteCollectionResponse;
import com.recycleBusiness.RecyclePal.exception.WasteNotCreated;

public interface WasteCollectionRequestServices {
    WasteCollectionResponse createRequestDetails(WasteCollectionRequestDto requestDto) throws WasteNotCreated;

    WasteCollectionResponse updateRequestDetails(WasteCollectionRequestDto requestDto);

}
