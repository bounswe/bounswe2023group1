package com.groupa1.resq.controller;

import com.groupa1.resq.entity.Need;
import com.groupa1.resq.entity.Notification;
import com.groupa1.resq.service.NotificationService;
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
}
