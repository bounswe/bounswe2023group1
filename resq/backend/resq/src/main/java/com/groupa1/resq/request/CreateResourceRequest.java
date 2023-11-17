package com.groupa1.resq.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateResourceRequest {
    private Long senderId;
    private String categoryTreeId;
    private Integer quantity;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
