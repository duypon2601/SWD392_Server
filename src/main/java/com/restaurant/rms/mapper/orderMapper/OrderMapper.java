package com.restaurant.rms.mapper.orderMapper;

import com.restaurant.rms.dto.request.orderDTO.OrderDTO;
import com.restaurant.rms.entity.DiningTable;
import com.restaurant.rms.entity.Order;
import com.restaurant.rms.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    @Autowired
    private OrderItemMapper orderItemMapper;

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
                    .map(orderItemDTO -> orderItemMapper.toEntity(orderItemDTO, order, null)) // Truyền thêm order vào
                    .collect(Collectors.toList()));
        }

        return order;
    }

}
