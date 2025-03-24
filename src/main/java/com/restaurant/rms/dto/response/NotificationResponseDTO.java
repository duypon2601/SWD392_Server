package com.restaurant.rms.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NotificationResponseDTO {
    private int notificationId; // Thay int thành Long
    private int userId;         // Thay int thành Long
    private String title;
    private String body;
    private LocalDateTime sentAt;
    private boolean isSent;
}