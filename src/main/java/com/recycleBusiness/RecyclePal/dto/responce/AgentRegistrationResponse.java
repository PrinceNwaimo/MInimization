package com.recycleBusiness.RecyclePal.dto.responce;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentRegistrationResponse {
    private Long id;
    private String message;
}
