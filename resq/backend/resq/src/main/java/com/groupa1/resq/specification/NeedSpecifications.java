package com.groupa1.resq.specification;

import com.groupa1.resq.entity.Need;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class NeedSpecifications {

    public static Specification<Need> hasCategoryTreeId(String categoryTreeId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("categoryTreeId"), categoryTreeId);
    }

    public static Specification<Need> hasLatitude(BigDecimal latitude) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("latitude"), latitude);
    }

    public static Specification<Need> hasLongitude(BigDecimal longitude) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("longitude"), longitude);
    }

    public static Specification<Need> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("description"), description);
    }

    public static Specification<Need> hasQuantity(Integer quantity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("quantity"), quantity);
    }

    public static Specification<Need> hasRequester(Long requesterId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("requester").get("id"), requesterId);
    }




}
