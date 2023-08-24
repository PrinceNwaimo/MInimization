package com.recycleBusiness.RecyclePal.data.repository;

import com.recycleBusiness.RecyclePal.data.models.Ecopal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EcopalRepository extends JpaRepository<Ecopal,Long> {

    Optional<Ecopal> findByEmail(String email);

//    Optional<Ecopal> findEcopalByUsername(String username);

}
