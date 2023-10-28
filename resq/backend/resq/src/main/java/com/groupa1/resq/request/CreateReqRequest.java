package com.groupa1.resq.request;

import com.groupa1.resq.entity.Need;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CreateReqRequest {
    private Long userId;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Set<Need> needs;
}
