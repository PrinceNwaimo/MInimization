package com.recycleBusiness.RecyclePal.service;

import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.recycleBusiness.RecyclePal.dto.request.AgentLoginRequest;
import com.recycleBusiness.RecyclePal.dto.request.AgentRegistrationRequest;
import com.recycleBusiness.RecyclePal.dto.request.CollectWasteRequest;
import com.recycleBusiness.RecyclePal.dto.request.UpdateAgentRequest;
import com.recycleBusiness.RecyclePal.dto.responce.*;
import com.recycleBusiness.RecyclePal.exception.*;

import java.util.List;

public interface AgentService {
    AgentRegistrationResponse register(AgentRegistrationRequest agentRegistrationRequest) throws AgentRegistrationFailedException;

    AgentLoginResponse login(AgentLoginRequest agentLoginRequest) throws AgentNotFoundException, InvalidDetailsException;

    AgentResponse getAgentById(Long Id) throws RecycleException, UsernameNotFoundException;

    ApiResponse<?> verifyAgent(String token)throws RecycleException, UsernameNotFoundException;

    List<AgentResponse> getAllAgents(int page, int items);

    ApiResponse<?> deleteAgent(Long Id);

    void deleteAll();

    ApiResponse<?> updateAgentDetails(Long id, UpdateAgentRequest updateAgentRequest) throws UsernameNotFoundException, IllegalAccessException, JsonPointerException, ProfileUpdateFailedException;
    FoundWasteResponse pickUpWaste(CollectWasteRequest request);
}
