package com.groupa1.resq.specification;

import com.groupa1.resq.entity.Action;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

public class ActionSpecifications {

    public static Specification<Action> hasTaskId(Long taskId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("task").get("id"), taskId);
    }

    public static Specification<Action> hasVerifierId(Long verifierId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("verifier").get("id"), verifierId);
    }

    public static Specification<Action> hasCompleted (boolean isCompleted) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isCompleted"), isCompleted);
    }

    public static Specification<Action> hasVerified (boolean isVerified) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isVerified"), isVerified);
    }

    public static Specification<Action> hasDueDate (LocalDateTime dueDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("dueDate"), dueDate);
    }
}