package com.restaurant.rms.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateRestaurantMenuItemDTO {
    private Long id;
    private Long menuId;
    private Integer foodId;
    private BigDecimal price;
    private Integer stockQuantity;
    private Boolean isAvailable;
}

