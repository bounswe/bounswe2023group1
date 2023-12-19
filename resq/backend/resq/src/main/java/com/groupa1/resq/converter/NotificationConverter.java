package com.groupa1.resq.converter;

import com.groupa1.resq.dto.NotificationDto;
import com.groupa1.resq.entity.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationConverter {

    public NotificationDto convertToDto(Notification notification) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(notification.getId());
        notificationDto.setCreatedAt(notification.getCreatedAt());
        notificationDto.setModifiedAt(notification.getModifiedAt());
        notificationDto.setNotificationType(notification.getNotificationType().toString());
        notificationDto.setUserId(notification.getUser().getId());
        notificationDto.setTitle(notification.getTitle());
        notificationDto.setBody(notification.getBody());
        notificationDto.setRead(notification.isRead());
        notificationDto.setRelatedEntityId(notification.getRelatedEntityId());
        notificationDto.setNotificationType(notification.getNotificationType().toString());
        return notificationDto;
    }
}
