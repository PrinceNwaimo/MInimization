package com.recycleBusiness.RecyclePal.data.repository;

import com.recycleBusiness.RecyclePal.data.models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByEmail(String email);

//    Optional<Agent> findByUsername(String username);
}
