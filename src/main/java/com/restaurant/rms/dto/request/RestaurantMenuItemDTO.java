package com.restaurant.rms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantMenuItemDTO {
    private int menu_item_id;
    private BigDecimal price;
    private int stock_quantity;
    private int min_stock_threshold;
    private int restaurant_id;
    private int food_id;
}
