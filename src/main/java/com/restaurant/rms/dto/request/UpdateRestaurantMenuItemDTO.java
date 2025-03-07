package com.restaurant.rms.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateRestaurantMenuItemDTO {
    private BigDecimal price;
    private Integer quantity;
    private Boolean isAvailable;
}

