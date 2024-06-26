package com.recycleBusiness.RecyclePal.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AdminResponse {
    private Long id;
    private String name;
    private String email;
}
