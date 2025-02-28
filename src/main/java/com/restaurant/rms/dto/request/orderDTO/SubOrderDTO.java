package com.restaurant.rms.dto.request.orderDTO;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubOrderDTO {
    private int id;
    private int orderId;
    private String status;
    private BigDecimal totalPrice;
    private List<SubOrderItemDTO> subOrderItems;
}
