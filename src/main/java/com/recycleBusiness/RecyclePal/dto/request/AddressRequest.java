package com.recycleBusiness.RecyclePal.dto.request;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    private String houseNumber;
    private String streetName;
    private String city;
}
