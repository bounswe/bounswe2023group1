package com.groupa1.resq.request;

import lombok.Data;

import java.util.List;

@Data
public class AddResourceToTaskRequest {
    private Long taskId;
    private Long receiverId;
    private List<Long> resourceIds;


}
