package com.restaurant.rms.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantMenuItemDTO {
    private int id;
    private int restaurantId;
    private int restaurantMenuId;
    private int foodId;
    private BigDecimal price;

    private boolean isAvailable;
    private String foodName;    // 🌟 Tên món ăn
    private String categoryName;
}
//    private int stockQuantity;
//    private int minStockThreshold;

