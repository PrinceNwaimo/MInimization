package com.recycleBusiness.RecyclePal.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminLoginResponse {
    private Long id;
    private String message;
}
