package com.recycleBusiness.RecyclePal.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.recycleBusiness.RecyclePal.data.models.Agent;
import com.recycleBusiness.RecyclePal.data.repository.AgentRepository;
import com.recycleBusiness.RecyclePal.dto.request.AgentLoginRequest;
import com.recycleBusiness.RecyclePal.dto.request.AgentRegistrationRequest;
import com.recycleBusiness.RecyclePal.dto.request.CollectWasteRequest;
import com.recycleBusiness.RecyclePal.dto.request.UpdateAgentRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.recycleBusiness.RecyclePal.utils.AppUtils.*;
import static com.recycleBusiness.RecyclePal.utils.ExceptionUtils.*;
import static com.recycleBusiness.RecyclePal.utils.ResponseUtils.*;

@Service
@AllArgsConstructor
@Slf4j
public class AgentServiceImplementation  implements AgentService{
    private final AgentRepository agentRepository;

    private final ModelMapper modelMapper;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;


    @Override
    public AgentRegistrationResponse register(AgentRegistrationRequest agentRegistrationRequest) throws AgentRegistrationFailedException {
        Agent agent = modelMapper.map(agentRegistrationRequest, Agent.class);
        agent.setPassword(passwordEncoder.encode(agent.getPassword()));
        if (agentExists(agentRegistrationRequest.getEmail())) throw new IllegalArgumentException(agentRegistrationRequest.getEmail() + "already exist");
        agentRepository.save(agent);
        return buildRegisterAgentResponse(agent.getAgentId());

    }
    private boolean agentExists(String email){
     Optional<Agent> found = agentRepository.findByEmail(email);
     return found.isPresent();
    }

    @Override
    public AgentLoginResponse login(AgentLoginRequest agentLoginRequest) throws RecycleException, UsernameNotFoundException {
        var foundAgent =agentRepository.findByEmail(agentLoginRequest.getEmail()).orElseThrow(()->new AgentNotFoundException("This is not a registered Agent, Kindly Register"))  ;
        verifyAgent(foundAgent.getEmail());
        if(!passwordEncoder.matches(agentLoginRequest.getPassword(),foundAgent.getPassword())) {
            throw new InvalidDetailsException("Wrong login details, Kindly try again with the right information");
        }
        return buildLoginAgentResponse(foundAgent.getAgentId());
    }


    @Override
    public AgentResponse getAgentById(Long id) throws RecycleException, UsernameNotFoundException {
        Optional<Agent> foundAgent = agentRepository.findById(id);
        Agent agent = foundAgent.orElseThrow(() -> new UsernameNotFoundException(
                String.format(USER_WITH_ID_NOT_FOUND, id)
        ));
        AgentResponse agentResponse = buildAgentResponse(agent);
        return agentResponse;
    }

    @Override
    public ApiResponse<?> verifyAgent(String token) throws RecycleException, UsernameNotFoundException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtUtils.getSecret().getBytes()))
                .build().verify(token);
        if (decodedJWT == null) throw new RecycleException(ACCOUNT_VERIFICATION_FAILED);
        Claim claim = decodedJWT.getClaim(ID);
        Long id = claim.asLong();
        Agent foundAgent = agentRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_WITH_ID_NOT_FOUND, id)));
        foundAgent.setIsEnabled(true);
        agentRepository.save(foundAgent);
        return ApiResponse.builder()
                .message(ACCOUNT_VERIFIED_SUCCESSFULLY)
                .build();

    }

    @Override
    public List<AgentResponse> getAllAgents(int page, int items) {
        Pageable pageable = buildPageRequest(page, items);
        Page<Agent> agentPage = agentRepository.findAll(pageable);
        List<Agent> agents = agentPage.getContent();
        return agents.stream()
                .map(AgentServiceImplementation::buildAgentResponse)
                .toList();

    }

    private static AgentResponse buildAgentResponse(Agent agent) {
        return AgentResponse.builder()
                .id(agent.getAgentId())
                .email(agent.getEmail())
                .name(agent.getFirstName() + EMPTY_SPACE_VALUE + agent.getLastName())
                .build();
    }

    @Override
    public ApiResponse<?> deleteAgent(Long Id) {
        agentRepository.deleteById(Id);
        return ApiResponse.builder()
                .message(USER_DELETED_SUCCESSFULLY)
                .build();
    }

    @Override
    public void deleteAll() {
        agentRepository.deleteAll();
    }

    @Override
    public ApiResponse<?> updateAgentDetails(Long id, UpdateAgentRequest updateAgentRequest) throws UsernameNotFoundException, IllegalAccessException, JsonPointerException, ProfileUpdateFailedException {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, id)));

        MultipartFile image = updateAgentRequest.getProfileImage();
        JsonPatch jsonPatch = buildUpdatePatch(updateAgentRequest);

        Agent updatedAgent = updateAgent(agent, jsonPatch, image);
        agentRepository.save(updatedAgent);

        return ApiResponse.builder()
                .message(PROFILE_UPDATED_SUCCESSFULLY)
                .build();
    }

    @Override
    public FoundWasteResponse pickUpWaste(CollectWasteRequest request) {
        return null;
    }

    private JsonPatch buildUpdatePatch(UpdateAgentRequest updateAgentRequest) throws IllegalAccessException, JsonPointerException {
        List<JsonPatchOperation> operations = new ArrayList<>();
        List<String> updateFields =
                List.of("bankName", "accountName", "AccountNumber", "Email", "Password", "ProfileImage");
        Field[] fields = updateAgentRequest.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            if (field.get(updateAgentRequest) != null &&
                    !updateFields.contains(field.getName())) {
                var operation = new ReplaceOperation(
                        new JsonPointer("/" + field.getName()),
                        new TextNode(field.get(updateAgentRequest).toString())

                );
                operations.add(operation);
            } else if (field.get(updateAgentRequest) != null &&
                    updateFields.contains(field.getName()) && !field.getName().equals("profileImage")) {
                if (field.getName().contains("bank") || field.getName().contains("account")) {
                    var operation = new ReplaceOperation(
                            new JsonPointer("/bankAccount/" + field.getName()),
                            new TextNode(field.get(updateAgentRequest).toString())
                    );
                    operations.add(operation);
                } else {
                    var operation = new ReplaceOperation(
                            new JsonPointer("/bioData/" + field.getName()),
                            new TextNode(field.get(updateAgentRequest).toString())
                    );
                    operations.add(operation);

                }
            }
        }
        return new JsonPatch(operations);
    }

    private Agent updateAgent(Agent agent, JsonPatch jsonPatch, MultipartFile image) throws ProfileUpdateFailedException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Patch {}", jsonPatch.toString());
        JsonNode agentNode = mapper.convertValue(agent, JsonNode.class);
        try {
            JsonNode updatedNode = jsonPatch.apply(agentNode);

            Agent updatedAgent = mapper.convertValue(updatedNode, Agent.class);
            return updatedAgent;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProfileUpdateFailedException(PROFILE_UPDATE_FAILED);
        }
    }

    private static AgentRegistrationResponse buildRegisterAgentResponse(Long agentId) {
        AgentRegistrationResponse agentRegistrationResponse = new AgentRegistrationResponse();
        agentRegistrationResponse.setMessage(AGENT_REGISTRATION_SUCCESSFUL);
        agentRegistrationResponse.setId(agentId);

        return agentRegistrationResponse;
    }
    private static AgentLoginResponse buildLoginAgentResponse(Long agentId){
        AgentLoginResponse agentLoginResponse = new AgentLoginResponse();
        agentLoginResponse.setMessage(AGENT_LOGIN_SUCCESSFUL);
        agentLoginResponse.setId(agentId);

        return agentLoginResponse;
    }
}
