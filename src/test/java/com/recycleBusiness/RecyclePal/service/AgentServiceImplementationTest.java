package com.recycleBusiness.RecyclePal.service;

import com.recycleBusiness.RecyclePal.dto.request.AgentRegistrationRequest;
import com.recycleBusiness.RecyclePal.dto.response.AgentRegistrationResponse;
import com.recycleBusiness.RecyclePal.exception.AgentRegistrationFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AgentServiceImplementationTest {

    @Autowired
    AgentService agentService;

    AgentRegistrationResponse agentRegistrationResponse;

    AgentRegistrationRequest agentRegistrationRequest;

    @BeforeEach
    public void setUp() throws AgentRegistrationFailedException {
        agentRegistrationRequest = new AgentRegistrationRequest();
        agentRegistrationRequest.setEmail("Prince@gmail.com");
        agentRegistrationRequest.setPassword("1234");

        agentRegistrationResponse = agentService.register(agentRegistrationRequest);
    }

    @Test
    public void testThatAgentCanRegister(){
        assertThat(agentRegistrationResponse).isNotNull();

    }

}
