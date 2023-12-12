package com.groupa1.resq.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateEventRequest {
    private Long reporterId;
    private String description;

    private BigDecimal eventLatitude;
    private BigDecimal eventLongitude;

    //private LocalDateTime reportDate;



}
