package com.restaurant.rms.mapper;

import com.restaurant.rms.dto.request.NotificationDTO;
import com.restaurant.rms.entity.NotificationEntity;
import com.restaurant.rms.entity.User;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class NotificationMapper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public NotificationDTO toDTO(NotificationEntity entity) {
        if (entity == null) {
            return null;
        }
        return NotificationDTO.builder()
                .id(entity.getNotificationId())
                .title(entity.getTitle())
                .body(entity.getBody())
                .isSent(entity.isSent())
                .sentAt(entity.getSentAt() != null ? DATE_FORMAT.format(entity.getSentAt()) : null)
                .userId(entity.getUser().getUser_id())
                .isDeleted(entity.isDeleted())
                .build();
    }

    public NotificationEntity toEntity(NotificationDTO dto, User user) {
        if (dto == null) {
            return null;
        }
        return NotificationEntity.builder()
                .notificationId(dto.getId())
                .title(dto.getTitle())
                .body(dto.getBody())
                .isSent(dto.isSent())
                .sentAt(dto.getSentAt() != null ? new java.util.Date() : null) // Chuyển đổi đơn giản, có thể cần parse
                .user(user)
                .isDeleted(dto.isDeleted())
                .build();
    }
}