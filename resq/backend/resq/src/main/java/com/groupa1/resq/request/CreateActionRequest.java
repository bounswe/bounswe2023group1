package com.groupa1.resq.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateActionRequest {
    private Long taskId;
    private Long verifierId;
    private String description;
    private boolean isCompleted;
    private BigDecimal startLatitude;
    private BigDecimal startLongitude;

    private BigDecimal endLatitude;
    private BigDecimal endLongitude;



}
