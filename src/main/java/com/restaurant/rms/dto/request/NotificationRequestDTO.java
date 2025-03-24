package com.restaurant.rms.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDTO {
    private int userId; // Thay int thành Long để đồng bộ với entity
    private String title;
    private String body;
}