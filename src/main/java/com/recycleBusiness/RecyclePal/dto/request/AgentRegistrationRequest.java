package com.recycleBusiness.RecyclePal.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.recycleBusiness.RecyclePal.data.models.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentRegistrationRequest {
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("address")
    private Address address;

}
