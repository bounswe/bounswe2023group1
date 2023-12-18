package com.groupa1.resq.specification;

import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.enums.EStatus;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {

    public static Specification<Task> hasAssignee(Long assigneeId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("assignee").get("id"), assigneeId);
    }

    public static Specification<Task> hasAssigner(Long assignerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("assigner").get("id"), assignerId);
    }

    public static Specification<Task> hasStatus(EStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }



}
