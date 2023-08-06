package com.recycleBusiness.RecyclePal.dto.responce;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerRegistrationResponse {
    private String username;
    private String message;
}
