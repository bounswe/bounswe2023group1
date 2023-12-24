package com.groupa1.resq.request;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private Long userId;
    private Long actionId;
    private String comment;
}
