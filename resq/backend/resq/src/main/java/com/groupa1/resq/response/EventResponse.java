package com.groupa1.resq.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class EventResponse {
    private long id;
    private long reporterId;
    private String description;
    private boolean isCompleted;
    private BigDecimal eventLatitude;
    private BigDecimal eventLongitude;
    private LocalDateTime reportDate;



}
