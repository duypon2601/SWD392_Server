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
    private Integer menuItemId;
    private int quantity;
    private BigDecimal price;
    private String menuItemName;
    private BigDecimal totalPrice; // 🟢 Không cần lưu vào DB, tính toán khi trả về DTO

    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}

