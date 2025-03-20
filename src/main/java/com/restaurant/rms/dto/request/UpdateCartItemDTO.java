package com.restaurant.rms.dto.request;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCartItemDTO {
    private Integer menuItemId;  // ID của món ăn trong menu
    private Integer quantity;    // Số lượng cần cập nhật
}