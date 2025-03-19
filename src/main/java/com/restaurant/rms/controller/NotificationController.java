package com.restaurant.rms.controller;
import com.restaurant.rms.entity.Notification;
import com.restaurant.rms.service.notificationService.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/notification")
@SecurityRequirement(name = "api")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(@RequestBody String message) {
        Notification notification = notificationService.sendNotification(message);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications() {
        List<Notification> notifications = notificationService.getUnreadNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/read")
    public ResponseEntity<List<Notification>> getReadNotifications() {
        List<Notification> notifications = notificationService.getReadNotifications();
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read");
    }
}

