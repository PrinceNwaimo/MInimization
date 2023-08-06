package com.recycleBusiness.RecyclePal.data.repository;


import com.recycleBusiness.RecyclePal.data.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

}
