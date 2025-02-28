package com.restaurant.rms.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    private int foodId;
    private Integer menuItemId;
    private String name;
    private int quantity;
    private BigDecimal price;
}
