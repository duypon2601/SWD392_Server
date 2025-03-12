package com.restaurant.rms.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodItemDTO {
    private Integer foodId;
    private BigDecimal price;
//    private Integer quantity;
}