package com.recycleBusiness.RecyclePal.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EcopalUpdateResponse {
    private String message;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private String phoneNumber;
}
