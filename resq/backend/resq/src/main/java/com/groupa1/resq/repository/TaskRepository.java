package com.groupa1.resq.repository;

import com.groupa1.resq.entity.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long>{


    @Query("SELECT t FROM Task t WHERE t.assignee.id = ?1")
    Optional<List<Task>> findByAssignee(Long userId);

    List<Task> findAll(Specification<Task> specification);

}
