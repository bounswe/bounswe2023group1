package com.groupa1.resq.specification;

import com.groupa1.resq.entity.Action;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;


public class ActionSpecifications {

    public static Specification<Action> hasTaskId(Long taskId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("task").get("id"), taskId);
    }

    public static Specification<Action> hasVerifier(Long verifierId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("verifier").get("id"), verifierId);
    }

    public static Specification<Action> hasCompleted(Boolean completed) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isCompleted"), completed);
    }

    public static Specification<Action> hasLatestDueDate(LocalDateTime dueDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("dueDate"), dueDate);
    }
    public static Specification<Action> hasVerified (boolean isVerified) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isVerified"), isVerified);
    }
    public static Specification<Action> hasEarliestDueDate(LocalDateTime dueDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("dueDate"), dueDate);
    }


}
