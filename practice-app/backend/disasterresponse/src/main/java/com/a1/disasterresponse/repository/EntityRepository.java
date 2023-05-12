package com.a1.disasterresponse.repository;

import com.a1.disasterresponse.model.EntityData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Pair;

public interface EntityRepository extends JpaRepository<EntityData.EntityRecord, String> {
}
