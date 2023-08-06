package com.recycleBusiness.RecyclePal.dto.request;

import com.recycleBusiness.RecyclePal.data.models.Address;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCustomerRequest {
    private String username;
    private String firstname;
    private String lastname;
    private AddressRequest address;
    private String phoneNumber;

}
