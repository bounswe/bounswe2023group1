package com.groupa1.resq.dto;

import com.groupa1.resq.entity.enums.ENeedStatus;
import lombok.Data;

import java.math.BigDecimal;

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
}
