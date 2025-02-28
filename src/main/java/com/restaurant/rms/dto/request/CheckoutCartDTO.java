package com.restaurant.rms.dto.request;


import com.restaurant.rms.dto.request.orderDTO.OrderItemDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutCartDTO {
    private int orderId;
    private int diningTableId;
    private String status;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> orderItems;
}

