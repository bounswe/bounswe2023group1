package com.groupa1.resq.repository;


import com.groupa1.resq.entity.Event;
import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByReporter(User reporter);

}
