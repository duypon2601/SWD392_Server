package com.restaurant.rms.controller;

import com.restaurant.rms.dto.request.NotificationDTO;
import com.restaurant.rms.dto.request.NotificationRequestDTO;
import com.restaurant.rms.entity.NotificationEntity;
import com.restaurant.rms.entity.User;
import com.restaurant.rms.repository.NotificationRepository;
import com.restaurant.rms.repository.UserRepository;
import com.restaurant.rms.mapper.NotificationMapper;
import com.restaurant.rms.service.notificationService.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/notifications")
@SecurityRequirement(name = "api")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;
    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationRepository.findAll().stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable int id) {
        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found or has been deleted"));
        return ResponseEntity.ok(notificationMapper.toDTO(notification));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable int userId) {
        List<NotificationDTO> notifications = notificationRepository.findByUser_UserId(userId).stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notifications);
    }

    // Sửa API tạo thông báo để dùng NotificationRequestDTO và gọi service
    @PostMapping
    public ResponseEntity<NotificationDTO> sendNotification(@RequestBody NotificationRequestDTO requestDTO) {
        // Gọi service để gửi và lưu thông báo
        NotificationEntity savedNotification = notificationService.sendNotification(
                String.valueOf(requestDTO.getUserId()),
                requestDTO.getTitle(),
                requestDTO.getBody()
        );

        // Chuyển đổi sang DTO để trả về
        NotificationDTO responseDTO = notificationMapper.toDTO(savedNotification);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable int id) {
        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found or has been deleted"));
        notification.setDeleted(true);
        notificationRepository.save(notification);
        return ResponseEntity.ok("Notification soft deleted successfully");
    }

    @GetMapping("/deleted")
    public ResponseEntity<List<NotificationDTO>> getAllDeletedNotifications() {
        List<NotificationDTO> notifications = notificationRepository.findAllDeleted().stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<NotificationDTO> restoreNotification(@PathVariable int id) {
        NotificationEntity notification = notificationRepository.findDeletedById(id)
                .orElseThrow(() -> new RuntimeException("Deleted Notification not found"));
        notification.setDeleted(false);
        NotificationEntity restoredEntity = notificationRepository.save(notification);
        return ResponseEntity.ok(notificationMapper.toDTO(restoredEntity));
    }
}