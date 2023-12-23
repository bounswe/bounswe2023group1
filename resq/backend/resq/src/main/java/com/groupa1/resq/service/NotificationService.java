package com.groupa1.resq.service;

import com.groupa1.resq.config.firebase.PushNotificationMessage;
import com.groupa1.resq.entity.Notification;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.ENotificationEntityType;
import com.groupa1.resq.exception.NotOwnerException;
import com.groupa1.resq.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    //@Autowired
    //FirebaseMessagingService firebaseMessagingService;

    @Value("${resq.app.fcm.recipientToken}")
    private String recipientToken;

    public void sendNotification(String title, String body, Long userId, Long relatedEntityId, ENotificationEntityType notificationType) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setBody(body);
        notification.setRead(false);
        notification.setNotificationType(notificationType);
        notification.setRelatedEntityId(relatedEntityId);

        User user = userService.findById(userId);
        notification.setUser(user);

        notificationRepository.save(notification);
        sendPushNotification(title, body);
    }

    private void sendPushNotification(String title, String body) {
        PushNotificationMessage pushNotificationMessage = new PushNotificationMessage();
        pushNotificationMessage.setTitle(title);
        pushNotificationMessage.setBody(body);
        pushNotificationMessage.setRecipientToken(recipientToken);
        //firebaseMessagingService.sendNotificationByToken(pushNotificationMessage);
    }

    public List<Notification> viewAllNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findAllByUserId(userId);
        markNotificationsAsRead(notifications);
        return notificationRepository.findAllByUserId(userId);
    }

    public Notification viewNotificationById(Long userId, Long notificationId) {
        Notification notification = findNotificationById(notificationId);
        if (notification.getUser().getId() != userId) {
            throw new NotOwnerException("User is not the owner of this notification");
        }
        return notification;
    }

    private Notification findNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("Notification not found"));
    }

    private void markNotificationsAsRead(List<Notification> notifications) {
        notifications
                .stream()
                .filter(notification -> !notification.isRead())
                .forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);
    }
}
