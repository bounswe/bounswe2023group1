package com.groupa1.resq.dto;

import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Accessors(chain = true)
public class TaskDto {
    private long id;
    private long assignee;
    private long assigner;
    private Set<ActionDto> actions;
    private String description;
    private Set<ResourceDto> resources;
    private Set<FeedbackDto> feedbacks;
    private EUrgency urgency;
    private EStatus status;
    private LocalDateTime createdDate;

}
