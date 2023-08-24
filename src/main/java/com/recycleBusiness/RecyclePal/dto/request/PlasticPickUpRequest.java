package com.recycleBusiness.RecyclePal.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.recycleBusiness.RecyclePal.data.models.Address;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlasticPickUpRequest {
    private Integer requesterId;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate createdTime;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate pickedUptime;
    private String quantity;
    private Address address;
    private boolean isPicked;
}
