package com.groupa1.resq.repository;

import com.groupa1.resq.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends
        JpaRepository<UserProfile, Long> {
}
