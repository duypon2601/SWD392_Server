package com.restaurant.rms.mapper;

import com.restaurant.rms.dto.request.NotificationRequestDTO;
import com.restaurant.rms.dto.response.NotificationResponseDTO;
import com.restaurant.rms.entity.Notification;
import com.restaurant.rms.entity.User;

public class NotificationMapper {

    public static Notification toEntity(NotificationRequestDTO requestDTO, User user) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(requestDTO.getTitle());
        notification.setBody(requestDTO.getBody());
        notification.setSentAt(null);
        notification.setIsSent(false);
        return notification;
    }

    public static NotificationResponseDTO toResponseDTO(Notification notification) {
        return NotificationResponseDTO.builder()
                .notificationId(notification.getNotificationId())
                .userId(notification.getUser().getUser_id())
                .title(notification.getTitle())
                .body(notification.getBody())
                .sentAt(notification.getSentAt())
                .isSent(notification.isSent())
                .build();
    }
}