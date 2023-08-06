package com.recycleBusiness.RecyclePal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Recipient {
    private final String name;
    private final String email;


}
