package com.groupa1.resq.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ActionDto {
    private Long id;
    private Long taskId;
    private long verifierId;
    private String description;
    private boolean isCompleted;
    private BigDecimal startLatitude;
    private BigDecimal startLongitude;
    private BigDecimal endLatitude;
    private BigDecimal endLongitude;
    private LocalDateTime dueDate;
    private LocalDateTime createdDate;



}
