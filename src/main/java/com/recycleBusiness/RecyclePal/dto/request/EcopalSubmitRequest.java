package com.recycleBusiness.RecyclePal.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EcopalSubmitRequest {
    private Long requesterId;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate createdTime;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate pickedUptime;
    private String quantity;
    private String description;
    private boolean isPicked;
}
