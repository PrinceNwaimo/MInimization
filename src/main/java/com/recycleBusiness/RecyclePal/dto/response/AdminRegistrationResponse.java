package com.recycleBusiness.RecyclePal.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminRegistrationResponse {
    private Long id;
    private String message;
}
