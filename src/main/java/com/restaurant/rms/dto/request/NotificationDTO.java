package com.restaurant.rms.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private int id;
    private String title;
    private String body;
    private boolean isSent;
    private String sentAt; // Dùng String để dễ hiển thị, có thể chuyển đổi từ Date
    private int userId;
    private boolean isDeleted;
}