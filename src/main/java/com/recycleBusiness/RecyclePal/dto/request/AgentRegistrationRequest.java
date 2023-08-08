package com.recycleBusiness.RecyclePal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentRegistrationRequest {
    @JsonProperty("email")
    private String email;
    @JsonProperty("username")
    private String Username;
    @JsonProperty("password")
    private String password;
}
