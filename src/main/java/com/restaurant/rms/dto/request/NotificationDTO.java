package com.restaurant.rms.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationDTO {
    private int id;
    private String title;
    private String body;
    private boolean isSent;
    private String sentAt; // Chuỗi định dạng thời gian
    private int userId;
    private boolean isDeleted;
}