package com.groupa1.resq.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.groupa1.resq.entity.enums.EGender;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CreateTaskRequest {
    private Long assignerId;
    private Long assigneeId;
    private String description;
    private List<Action> actions;
    private List<Resource> resources;
    private EUrgency urgency;
    private EStatus status;

    @Data
    public static class Action {
        private Long verifierId;
        private String description;
        private BigDecimal startLatitude;
        private BigDecimal startLongitude;
        private BigDecimal endLatitude;
        private BigDecimal endLongitude;
    }

    @Data
    public static class Resource {
        private Long ownerId;
        private String categoryTreeId;
        private EGender gender;
        private Integer quantity;
        private BigDecimal latitude;
        private BigDecimal longitude;


    }


}

