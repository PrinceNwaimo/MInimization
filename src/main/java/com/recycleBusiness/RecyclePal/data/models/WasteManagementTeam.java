package com.recycleBusiness.RecyclePal.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table
public class WasteManagementTeam {
    @Id
    @SequenceGenerator(
            name = "WasteCollectionService_sequence",
            sequenceName = "WasteCollectionService_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamID;
    private String teamName;
    @ManyToMany
    private List<Customer> customers;

}
