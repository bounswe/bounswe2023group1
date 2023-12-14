package com.groupa1.resq.repository;


import com.groupa1.resq.entity.Event;
import com.groupa1.resq.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByReporter(User reporter);

}
