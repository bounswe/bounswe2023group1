package com.groupa1.resq.service;


import com.groupa1.resq.entity.*;
import com.groupa1.resq.entity.enums.ENotificationEntityType;
import com.groupa1.resq.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserService userService;


    @Test
    public void testSendNotification() {
        // Given
        String title = "Test Title";
        String body = "Test Body";
        Long userId = 1L;
        Long relatedEntityId = 2L;
        ENotificationEntityType notificationType = ENotificationEntityType.REQUEST;

        User user = new User();
        when(userService.findById(userId)).thenReturn(user);

        // When
        notificationService.sendNotification(title, body, userId, relatedEntityId, notificationType);

        // Then
        verify(userService, times(1)).findById(userId);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
public void testViewAllNotifications() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setRead(false);
        notification.setNotificationType(ENotificationEntityType.REQUEST);
        notification.setRelatedEntityId(2L);
        notification.setTitle("Test Title");
        notification.setBody("Test Body");

        List<Notification> notifications = List.of(notification);
        when(notificationRepository.findAllByUserId(userId)).thenReturn(notifications);

        // When
        List<Notification> result = notificationService.viewAllNotifications(userId);

        // Then
        verify(notificationRepository, times(2)).findAllByUserId(userId);
        assertEquals(notifications, result);
    }

    @Test
    public void testViewNotificationById() {
        // Given
        Long userId = 1L;
        Long notificationId = 2L;
        User user = new User();
        user.setId(userId);

        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setUser(user);
        notification.setRead(false);
        notification.setNotificationType(ENotificationEntityType.REQUEST);
        notification.setRelatedEntityId(2L);
        notification.setTitle("Test Title");
        notification.setBody("Test Body");

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        // When
        Notification result = notificationService.viewNotificationById(userId, notificationId);

        // Then
        verify(notificationRepository, times(1)).findById(notificationId);
        assertEquals(notification, result);
    }

    @Test
    public void testViewNotificationById_NotOwnerException() {
        // Given
        Long userId = 1L;
        Long notificationId = 2L;
        User user = new User();
        user.setId(userId);

        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setUser(user);
        notification.setRead(false);
        notification.setNotificationType(ENotificationEntityType.REQUEST);
        notification.setRelatedEntityId(2L);
        notification.setTitle("Test Title");
        notification.setBody("Test Body");

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        // When
        try {
            notificationService.viewNotificationById(3L, notificationId);
        } catch (Exception e) {
            // Then
            assertEquals("User is not the owner of this notification", e.getMessage());
        }
    }

}