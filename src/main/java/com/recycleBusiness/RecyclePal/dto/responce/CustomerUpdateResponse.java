package com.recycleBusiness.RecyclePal.dto.responce;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerUpdateResponse {
    private String message;
    private String firstname;
    private String lastname;
    private String email;
    private String address;
    private String phoneNumber;
}
