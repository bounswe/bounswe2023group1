package com.groupa1.resq.specification;

import com.groupa1.resq.entity.Request;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RequestSpecifications {

    public static Specification<Request> hasLatitude(BigDecimal latitude) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("latitude"), latitude);
    }

    public static Specification<Request> hasLongitude(BigDecimal longitude) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("longitude"), longitude);
    }

    public static Specification<Request> hasUrgency(EUrgency urgency) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("urgency"), urgency);
    }

    public static Specification<Request> hasStatus(EStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Request> hasRequester(Long requesterId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("requester").get("id"), requesterId);
    }




}
