package com.recycleBusiness.RecyclePal.dto.request;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRegistrationRequest {
    private String username;
    private String email;
    private String password;
    private String address;
}
