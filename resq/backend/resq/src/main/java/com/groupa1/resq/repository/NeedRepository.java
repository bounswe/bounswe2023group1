package com.groupa1.resq.repository;

import com.groupa1.resq.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NeedRepository extends JpaRepository<Need, Long> {
    Optional<Need> findByRequester(<User> requester);

}