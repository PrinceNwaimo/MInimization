package com.recycleBusiness.RecyclePal.data.repository;

import com.recycleBusiness.RecyclePal.data.models.RecyclingCompanines;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecyclingCenterRepository extends JpaRepository<RecyclingCompanines,Integer> {
}
