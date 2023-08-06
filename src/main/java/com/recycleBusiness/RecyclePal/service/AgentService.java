package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.dto.request.CollectWasteRequest;
import com.recycleBusiness.RecyclePal.dto.responce.FoundWasteResponse;

public interface AgentService {
    FoundWasteResponse pickUpWaste(CollectWasteRequest request);
}
