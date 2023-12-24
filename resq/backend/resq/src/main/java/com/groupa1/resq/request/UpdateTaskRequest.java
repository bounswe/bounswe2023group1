package com.groupa1.resq.request;

import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import lombok.Data;

@Data
public class UpdateTaskRequest {
    private String description;
    private EUrgency urgency;
    private EStatus status;
}
