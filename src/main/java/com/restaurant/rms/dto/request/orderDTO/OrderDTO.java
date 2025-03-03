package com.restaurant.rms.dto.request.orderDTO;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private int id;
    private int diningTableId;
    private String status;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> orderItems;
}