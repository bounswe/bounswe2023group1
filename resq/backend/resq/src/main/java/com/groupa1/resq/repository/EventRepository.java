package com.groupa1.resq.repository;


import com.groupa1.resq.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
