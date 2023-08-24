package com.recycleBusiness.RecyclePal.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEcopalRequest {
    private String email;
    private String firstname;
    private String lastname;
    private AddressRequest address;
    private String phoneNumber;

}
