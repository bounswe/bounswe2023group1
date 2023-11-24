package com.groupa1.resq.specification;

import com.groupa1.resq.entity.Resource;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ResourceSpecifications {

    public static Specification<Resource> hasOwnerId(Long ownerId) {
        return (root, query, builder) -> builder.equal(root.get("ownerId"), ownerId);
    }

    public static Specification<Resource> hasCategoryTreeId(String categoryTreeId) {
        return (root, query, builder) -> builder.equal(root.get("categoryTreeId"), categoryTreeId);
    }

    public static Specification<Resource> hasLatitude(BigDecimal latitude) {
        return (root, query, builder) -> builder.equal(root.get("latitude"), latitude);
    }

    public static Specification<Resource> hasLongitude(BigDecimal longitude) {
        return (root, query, builder) -> builder.equal(root.get("longitude"), longitude);
    }

}
