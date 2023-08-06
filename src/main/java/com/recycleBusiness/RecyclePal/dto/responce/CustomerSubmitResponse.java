package com.recycleBusiness.RecyclePal.dto.responce;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.recycleBusiness.RecyclePal.data.models.Customer;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerSubmitResponse {
    private String message;
}
