package com.recycleBusiness.RecyclePal.data.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table
public class WasteCollectionAgent {
    @Id
    @SequenceGenerator(
            name = "WasteCollectionService_sequence",
            sequenceName = "WasteCollectionService_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceID;
    private String serviceName;
    private String serviceLocation;

}
