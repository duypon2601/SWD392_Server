package com.restaurant.rms.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantMenuDTO {
    private int restaurantId;
    private String name;
    private String description;
    private Boolean isActive;
    private List<FoodItemDTO> foodItems; // Danh sách món ăn
}