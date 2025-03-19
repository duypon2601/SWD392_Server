package com.restaurant.rms.dto.request.orderDTO;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDTO {
    private int id;
    private int menuItemId;
    private int quantity;
    private BigDecimal price;
    private String menuItemName;
}

