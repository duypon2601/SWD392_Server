package com.restaurant.rms.dto.request;


import com.restaurant.rms.dto.request.orderDTO.OrderItemDTO;
import com.restaurant.rms.dto.request.orderDTO.SubOrderItemDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutCartDTO {
    private Integer orderId;
    private Integer subOrderId;
    private int diningTableId;
    private String status;
    private BigDecimal totalPrice;
    private List<OrderItemDTO> orderItems;
    private List<SubOrderItemDTO> subOrderItems;
}

