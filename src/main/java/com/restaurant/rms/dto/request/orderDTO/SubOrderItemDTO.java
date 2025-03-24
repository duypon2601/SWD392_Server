package com.restaurant.rms.dto.request.orderDTO;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubOrderItemDTO {
    private int id;
    private Integer menuItemId;
    private int quantity;
    private BigDecimal price;
    private String menuItemName;
}
