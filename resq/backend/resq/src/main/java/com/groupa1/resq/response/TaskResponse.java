package com.groupa1.resq.response;

import com.groupa1.resq.entity.enums.EStatus;
import com.groupa1.resq.entity.enums.EUrgency;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class TaskResponse {
    private long id;
    private long assignee;
    private long assigner;
    private Set<ActionResponse> actions;
    private String description;
    private Set<ResourceResponse> resources;
    private Set<FeedbackResponse> feedbacks;
    private EUrgency urgency;
    private EStatus status;

}
