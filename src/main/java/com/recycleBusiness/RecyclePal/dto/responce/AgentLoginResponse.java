package com.recycleBusiness.RecyclePal.dto.responce;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentLoginResponse {
    private Long id;
    private String message;
}
