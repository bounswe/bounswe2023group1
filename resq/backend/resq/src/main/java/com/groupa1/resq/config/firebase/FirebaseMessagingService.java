package com.groupa1.resq.config.firebase;
/*
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService {
    @Autowired
    private FirebaseMessaging firebaseMessaging;

    public String sendNotificationByToken(PushNotificationMessage pushNotificationMessage) {
        Notification notification = Notification.builder()
                .setTitle(pushNotificationMessage.getTitle())
                .setBody(pushNotificationMessage.getBody())
                .build();

        Message message = Message.builder()
                .setToken(pushNotificationMessage.getRecipientToken())
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            return "Notification sent successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending notification.";
        }
    }
}
*/