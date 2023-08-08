package com.recycleBusiness.RecyclePal.controllers;

import com.recycleBusiness.RecyclePal.dto.request.AgentLoginRequest;
import com.recycleBusiness.RecyclePal.dto.request.AgentRegistrationRequest;
import com.recycleBusiness.RecyclePal.dto.request.UpdateAgentRequest;
import com.recycleBusiness.RecyclePal.dto.responce.AgentLoginResponse;
import com.recycleBusiness.RecyclePal.dto.responce.AgentRegistrationResponse;
import com.recycleBusiness.RecyclePal.dto.responce.ApiResponse;
import com.recycleBusiness.RecyclePal.exception.AgentNotFoundException;
import com.recycleBusiness.RecyclePal.exception.InvalidDetailsException;
import com.recycleBusiness.RecyclePal.exception.RecycleException;
import com.recycleBusiness.RecyclePal.exception.UsernameNotFoundException;
import com.recycleBusiness.RecyclePal.service.AgentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/agent")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class AgentController {
    private final AgentService agentService;

    @PostMapping("/register")
    public ResponseEntity<AgentRegistrationResponse> register(@RequestBody AgentRegistrationRequest agentRegistrationRequest){
        try{
            var response = agentService.register(agentRegistrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (RecycleException exception){
            var response = new AgentRegistrationResponse();
            response.setMessage(exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<AgentLoginResponse> login(@RequestBody AgentLoginRequest agentLoginRequest){
        try{
            var response = agentService.login(agentLoginRequest);
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        }catch(AgentNotFoundException | InvalidDetailsException exception){
            var response = new AgentLoginResponse();
            response.setMessage(exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllAgents(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(agentService.getAllAgents(page, size));
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyAgent(@RequestHeader(value = "token") String token){
        try {
            var response = agentService.verifyAgent(token);
            return ResponseEntity.ok(response);
        }catch (Exception | UsernameNotFoundException exception){
            ApiResponse<?> response = ApiResponse.builder()
                    .message(exception.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PatchMapping()
    public ResponseEntity<?> updateAgentAccount(@RequestParam Long id, @ModelAttribute UpdateAgentRequest updateAgentRequest){
        try{
            var response = agentService.updateAgentDetails(id, updateAgentRequest);
            return ResponseEntity.ok(response);
        }catch (Exception | UsernameNotFoundException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}