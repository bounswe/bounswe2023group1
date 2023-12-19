package com.groupa1.resq.controller;

import com.groupa1.resq.converter.NotificationConverter;
import com.groupa1.resq.dto.NotificationDto;
import com.groupa1.resq.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationConverter notificationConverter;

    @GetMapping("/viewAllNotifications")
    @PreAuthorize("hasRole('FACILITATOR') or hasRole('VICTIM') or hasRole('RESPONDER')")
    public List<NotificationDto> viewAllNotifications(@RequestParam Long userId) {
        log.info("Viewing all notifications");
        return notificationService.viewAllNotifications(userId).stream().map(notificationConverter::convertToDto).toList();
    }

    @GetMapping("/viewNotificationById")
    @PreAuthorize("hasRole('FACILITATOR') or hasRole('VICTIM') or hasRole('RESPONDER')")
    public NotificationDto viewNotificationById(@RequestParam Long notificationId, @RequestParam Long userId) {
        log.info("Viewing notification with id: {}, user id: {}", notificationId, userId);
        return notificationConverter.convertToDto(notificationService.viewNotificationById(userId, notificationId));
    }
}
