package com.groupa1.resq.dto;

import com.groupa1.resq.entity.enums.ENeedStatus;
import com.groupa1.resq.entity.enums.ESize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class NeedDto {
    private Long id;
    private Long userId;
    private String categoryTreeId;
    private String description;
    private Integer quantity;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long requestId;
    private ENeedStatus status;
    private LocalDateTime createdDate;
    private ESize size;
}
