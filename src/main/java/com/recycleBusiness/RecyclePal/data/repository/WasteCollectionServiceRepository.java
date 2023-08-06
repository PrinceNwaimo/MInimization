package com.recycleBusiness.RecyclePal.data.repository;

import com.recycleBusiness.RecyclePal.data.models.WasteCollectionAgent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteCollectionServiceRepository extends JpaRepository<WasteCollectionAgent,Integer> {
}
