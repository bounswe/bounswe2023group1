package com.groupa1.resq.repository;


import com.groupa1.resq.entity.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    List<Resource> findAll(Specification<Resource> specification);
    @Query(value = "SELECT * FROM RESOURCE  WHERE (6371 * acos(cos(radians(?1)) * cos(radians(latitude)) * cos(radians(longitude) - radians(?2)) + sin(radians(?1)) * sin(radians(latitude)))) < ?3", nativeQuery = true)
    List<Resource> filterByDistance(BigDecimal longitude, BigDecimal latitude, BigDecimal distance);
}
