package com.restaurant.rms.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRestaurantMenuDTO {
    private Integer restaurantId;
    private String name;
    private String description;
    private Boolean isActive;
    private List<Integer> foodIds; // Chỉ cần truyền danh sách foodId
}

