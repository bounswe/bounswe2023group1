package com.groupa1.resq.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ActionResponse {
    private long id;
    private long taskId;
    private long verifierId;
    private String description;
    private boolean isCompleted;
    private BigDecimal startLatitude;
    private BigDecimal startLongitude;
    private BigDecimal endLatitude;
    private BigDecimal endLongitude;
    private LocalDateTime dueDate;



}
