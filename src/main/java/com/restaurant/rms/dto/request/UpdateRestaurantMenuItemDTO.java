package com.restaurant.rms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRestaurantMenuItemDTO {
    private BigDecimal price;
    private Boolean isAvailable;
    private Integer restaurantMenuId;
}

