package com.recycleBusiness.RecyclePal.dto.request;

import com.recycleBusiness.RecyclePal.data.models.Address;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    private Address houseNumber;
    private Address streetName;
    private Address city;
    private Address state;
}
