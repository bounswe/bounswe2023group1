package com.groupa1.resq.repository;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface NeedRepository extends JpaRepository<Need, Long> {
    List<Need> findByRequester(User requester);

    List<Need> findByLongitudeAndLatitude(BigDecimal longitude, BigDecimal latitude);

    List<Need> findByIdAndRequester(Long userId, User requester);



}