package com.recycleBusiness.RecyclePal.dto.responce;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.recycleBusiness.RecyclePal.data.models.Address;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WasteCollectionResponse {
    private String message;
}
