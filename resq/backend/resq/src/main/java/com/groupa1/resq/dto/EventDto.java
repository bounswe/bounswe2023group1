package com.groupa1.resq.dto;


import com.groupa1.resq.entity.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventDto {

    private Long reporterId;

    private String description;

    private LocalDateTime reportDate;

    private boolean isVerified;

    private BigDecimal eventLatitude;
    private BigDecimal eventLongitude;


}
