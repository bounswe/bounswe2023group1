package com.groupa1.resq.repository;

import com.groupa1.resq.entity.Action;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionRepository extends JpaRepository<Action, Long> {

    List<Action> findAll(Specification<Action> specification);
}
