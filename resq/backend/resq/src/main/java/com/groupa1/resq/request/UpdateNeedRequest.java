package com.groupa1.resq.request;

import com.groupa1.resq.entity.enums.ESize;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateNeedRequest {
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String categoryTreeId;
    private Integer quantity;
    private ESize size;
    private Boolean isRecurent;
}
