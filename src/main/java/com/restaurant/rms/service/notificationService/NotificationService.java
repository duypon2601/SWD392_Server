package com.restaurant.rms.service.notificationService;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.restaurant.rms.dto.request.NotificationRequestDTO;
import com.restaurant.rms.dto.response.NotificationResponseDTO;
import com.restaurant.rms.entity.User;
import com.restaurant.rms.mapper.NotificationMapper;
import com.restaurant.rms.repository.NotificationRepository;
import com.restaurant.rms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public NotificationResponseDTO sendNotification(NotificationRequestDTO requestDTO) {
        // Tìm user theo userId
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + requestDTO.getUserId()));

        // Kiểm tra tokenDevice
        String tokenDevice = user.getTokenDevice();
        if (tokenDevice == null || tokenDevice.isEmpty()) {
            throw new RuntimeException("No device token found for user: " + user.getUsername());
        }

        // Tạo entity Notification để lưu vào DB
        com.restaurant.rms.entity.Notification notificationEntity = NotificationMapper.toEntity(requestDTO, user);

        // Gửi thông báo qua Firebase
        try {
            Notification notification = Notification.builder()
                    .setTitle(requestDTO.getTitle())
                    .setBody(requestDTO.getBody())
                    .build();

            Message message = Message.builder()
                    .setNotification(notification)
                    .setToken(tokenDevice)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent notification to user {}: {}", user.getUsername(), response);

            // Cập nhật trạng thái gửi thành công
            notificationEntity.setSentAt(LocalDateTime.now());
            notificationEntity.setIsSent(true);
        } catch (Exception e) {
            log.error("Failed to send notification to user {}: {}", user.getUsername(), e.getMessage());
            notificationEntity.setIsSent(false);
            // Lưu thông báo thất bại vào DB nhưng không ném ngoại lệ để không làm gián đoạn giao dịch
        }

        // Lưu thông báo vào DB
        notificationRepository.save(notificationEntity);

        return NotificationMapper.toResponseDTO(notificationEntity);
    }

    public void sendNotificationToUser(int userId, String title, String body) { // Thay int thành Long
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        String tokenDevice = user.getTokenDevice();
        if (tokenDevice == null || tokenDevice.isEmpty()) {
            log.warn("No device token found for user: {}", user.getUsername());
            return;
        }

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(tokenDevice)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent notification to user {}: {}", user.getUsername(), response);
        } catch (Exception e) {
            log.error("Failed to send notification to user {}: {}", user.getUsername(), e.getMessage());
        }
    }
}