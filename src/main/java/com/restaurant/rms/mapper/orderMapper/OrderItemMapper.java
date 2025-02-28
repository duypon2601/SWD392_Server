package com.restaurant.rms.mapper.orderMapper;

import com.restaurant.rms.dto.request.orderDTO.OrderItemDTO;
import com.restaurant.rms.entity.Order;
import com.restaurant.rms.entity.OrderItem;
import com.restaurant.rms.entity.RestaurantMenuItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItemDTO toDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        return OrderItemDTO.builder()
                .id(orderItem.getOrderItemId())
                .menuItemId(orderItem.getMenuItem().getMenu_item_id())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }

    public OrderItem toEntity(OrderItemDTO orderItemDTO, Order order, RestaurantMenuItem menuItem) {
        if (orderItemDTO == null) {
            return null;
        }
        return OrderItem.builder()
                .orderItemId(orderItemDTO.getId())
                .order(order)
                .menuItem(menuItem)
                .quantity(orderItemDTO.getQuantity())
                .price(orderItemDTO.getPrice())
                .build();
    }
}
