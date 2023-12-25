package com.groupa1.resq.request;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class UpdateReqRequest {
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private EStatus status;
    private EUrgency urgency;
}
