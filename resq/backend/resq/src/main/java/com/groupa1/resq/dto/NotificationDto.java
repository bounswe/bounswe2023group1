package com.groupa1.resq.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long userId;
    private String title;
    private String body;
    private boolean isRead;
    private Long relatedEntityId;
    private String notificationType;
}
