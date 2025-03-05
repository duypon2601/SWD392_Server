package com.restaurant.rms.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    private Integer menuItemId;  // ID của món ăn trong menu
    private String name;         // Tên món ăn
    private int quantity;        // Số lượng
    private BigDecimal price;    // Giá tiền cho mỗi món
}