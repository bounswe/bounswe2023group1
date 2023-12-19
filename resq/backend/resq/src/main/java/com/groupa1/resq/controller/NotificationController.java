package com.groupa1.resq.controller;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Notification;
import com.groupa1.resq.entity.enums.ENotificationEntityType;
import com.groupa1.resq.service.NotificationService;
import com.groupa1.resq.util.NotificationMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/viewAllNotifications")
    @PreAuthorize("hasRole('FACILITATOR') or hasRole('VICTIM') or hasRole('RESPONDER')")
    public List<Notification> viewAllNotifications(@RequestParam Long userId) {
        log.info("Viewing all notifications");
        return notificationService.viewAllNotifications(userId);
    }

    @GetMapping("/viewNotificationById")
    @PreAuthorize("hasRole('FACILITATOR') or hasRole('VICTIM') or hasRole('RESPONDER')")
    public Notification viewNotificationById(@RequestParam Long notificationId, @RequestParam Long userId) {
        log.info("Viewing notification with id: {}, user id: {}", notificationId, userId);
        return notificationService.viewNotificationById(userId, notificationId);
    }

    @PostMapping("/sendNotification")
    @PreAuthorize("hasRole('ADMIN')")
    public void sendSystemNotification(@RequestParam String title, @RequestParam Long userId,
                                       @RequestParam Long relatedEntityId, @RequestParam ENotificationEntityType notificationType) {
        String body = String.format(NotificationMessages.SYSTEM_MESSAGE, userId, relatedEntityId);
        log.info("Sending notification with title: {}, body: {}, user id: {}, related entity id: {}, notification type: {}"
                , title, body, userId, relatedEntityId, notificationType);
        notificationService.sendNotification(title, body, userId, relatedEntityId, notificationType);
    }

}
