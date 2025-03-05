package com.restaurant.rms.mapper.orderMapper;

import com.restaurant.rms.dto.request.orderDTO.OrderDTO;
import com.restaurant.rms.dto.request.orderDTO.OrderItemDTO;
import com.restaurant.rms.entity.DiningTable;
import com.restaurant.rms.entity.Order;
import com.restaurant.rms.entity.RestaurantMenuItem;
import com.restaurant.rms.enums.OrderStatus;
import com.restaurant.rms.repository.RestaurantMenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private RestaurantMenuItemRepository menuItemRepository; // ✅ Thêm repository để lấy menuItem

    public OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }
        return OrderDTO.builder()
                .id(order.getOrderId())
                .diningTableId(order.getDiningTable().getDiningTableId())
                .status(order.getStatus().name())
                .totalPrice(order.getTotalPrice())
                .orderItems(order.getOrderItems() != null ?
                        order.getOrderItems().stream()
                                .map(orderItemMapper::toDTO)
                                .collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
    }

    public Order toEntity(OrderDTO orderDTO, DiningTable diningTable) {
        if (orderDTO == null) {
            return null;
        }

        Order order = Order.builder()
                .orderId(orderDTO.getId())
                .diningTable(diningTable)
                .status(OrderStatus.valueOf(orderDTO.getStatus()))
                .totalPrice(orderDTO.getTotalPrice())
                .orderItems(new ArrayList<>()) // Khởi tạo danh sách rỗng trước
                .build();

        if (orderDTO.getOrderItems() != null) {
            order.setOrderItems(orderDTO.getOrderItems().stream()
                    .map(orderItemDTO -> {
                        // 🔍 Truy vấn menuItem từ database
                        RestaurantMenuItem menuItem = menuItemRepository.findById(orderItemDTO.getMenuItemId())
                                .orElseThrow(() -> new RuntimeException("Menu item not found with ID: " + orderItemDTO.getMenuItemId()));

                        return orderItemMapper.toEntity(orderItemDTO, order, menuItem);
                    })
                    .collect(Collectors.toList()));
        }

        return order;
    }

}
