package com.groupa1.resq.repository;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByRequester(User requester);

    List<Request> findByLongitudeAndLatitude(BigDecimal longitude, BigDecimal latitude);

    List<Request> findAll(Specification<Request> specification);

    //Haversine formula
    @Query(value = "SELECT * FROM NEED WHERE (6371 * acos(cos(radians(?1)) * cos(radians(latitude)) * cos(radians(longitude) - radians(?2)) + sin(radians(?1)) * sin(radians(latitude)))) < ?3", nativeQuery = true)
    List<Request> filterByDistance(BigDecimal longitude, BigDecimal latitude, BigDecimal distance);



}