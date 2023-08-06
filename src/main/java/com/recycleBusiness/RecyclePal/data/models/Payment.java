package com.recycleBusiness.RecyclePal.data.models;

import com.recycleBusiness.RecyclePal.data.models.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table
public class Payment {
    @Id
    @SequenceGenerator(
            name = "Payment_sequence",
            sequenceName = "Payment_sequence",
            allocationSize = 1
    )

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentID;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
