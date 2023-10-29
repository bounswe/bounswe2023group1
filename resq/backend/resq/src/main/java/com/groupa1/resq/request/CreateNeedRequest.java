package com.groupa1.resq.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateNeedRequest {
    private Long userId;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String categoryTreeId;
    private Integer quantity;
}
