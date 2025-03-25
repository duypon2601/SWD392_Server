package com.restaurant.rms.controller;


import com.restaurant.rms.dto.request.NotificationDTO;
import com.restaurant.rms.entity.NotificationEntity;
import com.restaurant.rms.entity.User;
import com.restaurant.rms.repository.NotificationRepository;
import com.restaurant.rms.repository.UserRepository;
import com.restaurant.rms.mapper.NotificationMapper;
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

    // Lấy tất cả thông báo (chưa xóa mềm)
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationRepository.findAll().stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notifications);
    }

    // Lấy thông báo theo ID
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable int id) {
        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found or has been deleted"));
        return ResponseEntity.ok(notificationMapper.toDTO(notification));
    }

    // Lấy thông báo theo userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable int userId) {
        List<NotificationDTO> notifications = notificationRepository.findByUser_UserId(userId).stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notifications);
    }

    // Tạo thông báo mới
    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        User user = userRepository.findById(notificationDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        NotificationEntity entity = notificationMapper.toEntity(notificationDTO, user);
        NotificationEntity savedEntity = notificationRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationMapper.toDTO(savedEntity));
    }

    // Xóa mềm thông báo
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable int id) {
        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found or has been deleted"));
        notification.setDeleted(true);
        notificationRepository.save(notification);
        return ResponseEntity.ok("Notification soft deleted successfully");
    }

    // Lấy danh sách thông báo đã xóa mềm
    @GetMapping("/deleted")
    public ResponseEntity<List<NotificationDTO>> getAllDeletedNotifications() {
        List<NotificationDTO> notifications = notificationRepository.findAllDeleted().stream()
                .map(notificationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notifications);
    }

    // Phục hồi thông báo đã xóa mềm
    @PutMapping("/restore/{id}")
    public ResponseEntity<NotificationDTO> restoreNotification(@PathVariable int id) {
        NotificationEntity notification = notificationRepository.findDeletedById(id)
                .orElseThrow(() -> new RuntimeException("Deleted Notification not found"));
        notification.setDeleted(false);
        NotificationEntity restoredEntity = notificationRepository.save(notification);
        return ResponseEntity.ok(notificationMapper.toDTO(restoredEntity));
    }
}