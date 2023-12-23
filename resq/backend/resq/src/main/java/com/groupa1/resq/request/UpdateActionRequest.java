package com.groupa1.resq.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateActionRequest {
    private Long taskId;
    private Long verifierId;
    private String description;
    private boolean isCompleted;
    private boolean isVerified;
    private BigDecimal startLatitude;
    private BigDecimal startLongitude;

    private BigDecimal endLatitude;
    private BigDecimal endLongitude;
    private LocalDateTime dueDate;



}
