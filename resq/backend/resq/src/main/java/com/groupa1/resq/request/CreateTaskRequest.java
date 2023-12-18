package com.groupa1.resq.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.groupa1.resq.entity.enums.EGender;
import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CreateTaskRequest {
    private Long assignerId;
    private Long assigneeId;
    private String description;
    private EUrgency urgency;

}

