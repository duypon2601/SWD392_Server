package com.restaurant.rms.service.notificationService;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.restaurant.rms.dto.request.NotificationRequestDTO;
import com.restaurant.rms.dto.response.NotificationResponseDTO;
import com.restaurant.rms.entity.NotificationEntity;
import com.restaurant.rms.entity.User;
import com.restaurant.rms.mapper.NotificationMapper;
import com.restaurant.rms.repository.NotificationRepository;
import com.restaurant.rms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(FirebaseMessaging firebaseMessaging, NotificationRepository notificationRepository, UserRepository userRepository) {
        this.firebaseMessaging = firebaseMessaging;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void sendNotification(String userId, String title, String body) {
        try {
            User user = userRepository.findById(Integer.parseInt(userId))
                    .orElseThrow(() -> new RuntimeException("User not found"));
            String tokenDevice = user.getTokenDevice();

            if (tokenDevice == null || tokenDevice.isEmpty()) {
                log.warn("User {} has no device token", userId);
                saveNotification(user, title, body, false);
                return;
            }

            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .setToken(tokenDevice)
                    .build();

            String response = firebaseMessaging.send(message);
            log.info("Notification sent successfully: {}", response);
            saveNotification(user, title, body, true);
        } catch (Exception e) {
            log.error("Failed to send notification to user {}: {}", userId, e.getMessage());
            saveNotification(userRepository.findById(Integer.parseInt(userId)).get(), title, body, false);
            throw new RuntimeException("Failed to send notification", e);
        }
    }

    private void saveNotification(User user, String title, String body, boolean isSent) {
        NotificationEntity notification = NotificationEntity.builder()
                .user(user)
                .title(title)
                .body(body)
                .isSent(isSent)
                .sentAt(isSent ? new Date() : null)
                .build();
        notificationRepository.save(notification);
    }
}