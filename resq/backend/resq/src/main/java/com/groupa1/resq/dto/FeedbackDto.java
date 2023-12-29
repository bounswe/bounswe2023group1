package com.groupa1.resq.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class FeedbackDto {
    private long id;
    private long taskId;
    private long userId;
    private String message;
    private LocalDateTime createdDate;



}
