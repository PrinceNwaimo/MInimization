package com.recycleBusiness.RecyclePal.data.repository;

import com.recycleBusiness.RecyclePal.data.models.WasteCollectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteCollectionRequestRepository extends JpaRepository<WasteCollectionRequest,Integer> {
}
