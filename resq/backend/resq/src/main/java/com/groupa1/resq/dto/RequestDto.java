package com.groupa1.resq.dto;

import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RequestDto {
    private Long id;
    private Long requesterId;
    private List<Long> needIds;
    private EUrgency urgency;
    private EStatus status;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String description;
    private LocalDateTime createdDate;

}
