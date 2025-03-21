package com.restaurant.rms.dto.request.orderDTO;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderItemDTO {
    private int quantity;
}