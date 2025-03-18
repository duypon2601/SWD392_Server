package com.restaurant.rms.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateRestaurantMenuItemDTO {
    private Integer restaurantMenuId; // Thay Long thành Integer cho đồng nhất với entity
    private Integer foodId;
    private BigDecimal price;
}

