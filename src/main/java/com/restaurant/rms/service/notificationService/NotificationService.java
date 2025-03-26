package com.restaurant.rms.service.notificationService;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.restaurant.rms.entity.NotificationEntity;
import com.restaurant.rms.entity.User;
import com.restaurant.rms.repository.NotificationRepository;
import com.restaurant.rms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(FirebaseMessaging firebaseMessaging,
                               NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.firebaseMessaging = firebaseMessaging;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public NotificationEntity sendNotification(String userId, String title, String body) {
        log.info("Starting to send notification to userId: {}, title: {}, body: {}", userId, title, body);

        User user = userRepository.findById(Integer.parseInt(userId))
                .orElseThrow(() -> {
                    log.error("User not found with userId: {}", userId);
                    return new RuntimeException("User not found");
                });

        String tokenDevice = user.getTokenDevice();
        log.debug("Retrieved tokenDevice for userId {}: {}", userId, tokenDevice);

        boolean isSent = false;
        if (tokenDevice != null && !tokenDevice.isEmpty()) {
            try {
                Message message = Message.builder()
                        .setNotification(Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build())
                        .setToken(tokenDevice)
                        .build();

                log.debug("Sending Firebase message to userId: {}", userId);
                String response = firebaseMessaging.send(message);
                log.info("Notification sent successfully to userId: {}, response: {}", userId, response);
                isSent = true;
            } catch (Exception e) {
                log.error("Failed to send notification to userId: {}. Error: {}", userId, e.getMessage(), e);
            }
        } else {
            log.warn("User {} has no device token, skipping Firebase send", userId);
        }

        NotificationEntity notification = NotificationEntity.builder()
                .user(user)
                .title(title)
                .body(body)
                .isSent(isSent)
                .sentAt(isSent ? new Date() : null)
                .isDeleted(false)
                .build();

        NotificationEntity savedNotification = notificationRepository.save(notification);
        log.info("Saved notification for userId: {}, isSent: {}, isDeleted: false", user.getUser_id(), isSent);
        return savedNotification;
    }

    private void saveNotification(User user, String title, String body, boolean isSent) {
        NotificationEntity notification = NotificationEntity.builder()
                .user(user)
                .title(title)
                .body(body)
                .isSent(isSent) // Set trong service
                .sentAt(isSent ? new Date() : null)
                .isDeleted(false) // Mặc định false khi tạo mới
                .build();

        notificationRepository.save(notification);
        log.info("Saved notification for userId: {}, isSent: {}, isDeleted: false", user.getUser_id(), isSent);
    }
}