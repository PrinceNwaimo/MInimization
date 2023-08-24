package com.recycleBusiness.RecyclePal.data.repository;

import com.recycleBusiness.RecyclePal.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);

//    Optional<Admin> findByUsername(String email);
}
