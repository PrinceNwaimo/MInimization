package com.recycleBusiness.RecyclePal.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table
public class WasteCollectionRequest {
    @Id
    @SequenceGenerator(
            name = "WasteCollectionRequest_sequence",
            sequenceName = "WasteCollectionRequest_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wasteId;
    private Integer requesterId;
    private Integer pickerId;
    private LocalDate createdTime;
    private LocalDate pickedUptime;
    private String quantity;
    private String description;
    @OneToOne
    private Address address;
    private boolean isPicked;
}
