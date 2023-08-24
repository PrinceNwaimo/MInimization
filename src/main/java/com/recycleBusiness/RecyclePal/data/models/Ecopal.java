package com.recycleBusiness.RecyclePal.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Ecopal {
    @Id

    private Long id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String firstname;
    private String lastname;
    private String password;
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;
    private String phoneNumber;

    @JsonIgnore
    private Boolean isEnabled;

}
