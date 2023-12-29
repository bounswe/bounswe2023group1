package com.groupa1.resq.repository;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface NeedRepository extends JpaRepository<Need, Long> {
    List<Need> findByRequester(User requester);

    List<Need> findByLongitudeAndLatitude(BigDecimal longitude, BigDecimal latitude);

    List<Need> findByIdAndRequester(Long userId, User requester);

    List<Need> findAll(Specification<Need> specification);

    List<Need> findAllByIsRecurrentTrue();


    //Haversine formula
    @Query(value = "SELECT * FROM NEED WHERE (6371 * acos(cos(radians(?1)) * cos(radians(latitude)) * cos(radians(longitude) - radians(?2)) + sin(radians(?1)) * sin(radians(latitude)))) < ?3", nativeQuery = true)
    List<Need> filterByDistance(BigDecimal longitude, BigDecimal latitude, BigDecimal distance);

}