package com.groupa1.resq.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FeedbackResponse {
    private long id;
    private long taskId;
    private long userId;
    private String message;



}
