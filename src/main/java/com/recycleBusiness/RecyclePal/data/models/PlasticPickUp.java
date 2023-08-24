package com.recycleBusiness.RecyclePal.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder

public class PlasticPickUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plasticId;
    private Long ecopalId;
    private Long pickerId;
    private LocalDateTime createdTime;
    private LocalDateTime pickedUptime;
    private String quantity;
    private String description;
    @OneToOne
    private Address address;
    private boolean isPicked;
}
