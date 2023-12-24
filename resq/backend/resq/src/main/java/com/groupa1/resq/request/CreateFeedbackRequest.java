package com.groupa1.resq.request;

import lombok.Data;

@Data
public class CreateFeedbackRequest {
    private Long userId;
    private Long taskId;
    private String message;
}
