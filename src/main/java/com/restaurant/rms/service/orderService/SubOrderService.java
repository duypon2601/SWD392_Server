package com.restaurant.rms.service.orderService;

import com.restaurant.rms.dto.request.orderDTO.OrderDTO;
import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
import com.restaurant.rms.dto.request.orderDTO.SubOrderItemDTO;
import com.restaurant.rms.entity.Order;
import com.restaurant.rms.entity.SubOrder;
import com.restaurant.rms.entity.SubOrderItem;
import com.restaurant.rms.entity.OrderItem;
import com.restaurant.rms.enums.OrderStatus;
import com.restaurant.rms.mapper.orderMapper.OrderItemMapper;
import com.restaurant.rms.mapper.orderMapper.SubOrderMapper;
import com.restaurant.rms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class SubOrderService {
    private final SubOrderRepository subOrderRepository;
    private final OrderRepository orderRepository;
    private final SubOrderMapper subOrderMapper;
    private final SubOrderItemRepository subOrderItemRepository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;


//    @Transactional
//    public void confirmSubOrder(int subOrderId) {
//        SubOrder subOrder = subOrderRepository.findById(subOrderId)
//                .orElseThrow(() -> new RuntimeException("SubOrder not found"));
//
//        Order order = subOrder.getOrder();
//
//        // Cộng dồn món ăn từ SubOrder vào Order chính
//        List<SubOrderItem> subOrderItems = subOrder.getSubOrderItems();
//        for (SubOrderItem subItem : subOrderItems) {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            orderItem.setMenuItem(subItem.getMenuItem());
//            orderItem.setQuantity(subItem.getQuantity());
//            orderItem.setPrice(subItem.getPrice());
//            order.getOrderItems().add(orderItem);
//        }
//
//        // Cập nhật tổng giá tiền của Order chính
//        BigDecimal totalSubOrderPrice = subOrder.getTotalPrice();
//        order.setTotalPrice(order.getTotalPrice().add(totalSubOrderPrice));
//
//        // Đặt trạng thái SubOrder thành CONFIRMED
//        subOrder.setStatus(OrderStatus.CONFIRMED);
//
//        // Lưu SubOrder trước
//        subOrderRepository.save(subOrder);
//
//        // Lưu Order (các trường createdAt và updatedAt sẽ được tự động cập nhật bởi @PrePersist/@PreUpdate)
//        orderRepository.save(order);
//    }
@Transactional
public Order confirmSubOrder(int subOrderId) {
    SubOrder subOrder = subOrderRepository.findById(subOrderId)
            .orElseThrow(() -> new RuntimeException("SubOrder not found"));

    Order order = subOrder.getOrder();

    // Kiểm tra createdAt trước khi cập nhật
    if (order.getCreatedAt() == null) {
        log.warn("Order {} has null createdAt before update", order.getOrderId());
        order.setCreatedAt(new Date()); // Thiết lập lại nếu null
    }

    // Lấy danh sách OrderItem hiện có của Order
    List<OrderItem> existingOrderItems = order.getOrderItems();

    // Cộng dồn món ăn từ SubOrder vào Order chính
    List<SubOrderItem> subOrderItems = subOrder.getSubOrderItems();
    for (SubOrderItem subItem : subOrderItems) {
        // Tìm OrderItem hiện có với cùng menuItem
        Optional<OrderItem> existingOrderItem = existingOrderItems.stream()
                .filter(orderItem -> orderItem.getMenuItem().getRestaurantMenuItemId() == subItem.getMenuItem().getRestaurantMenuItemId())
                .findFirst();

        if (existingOrderItem.isPresent()) {
            // Nếu đã tồn tại, cộng dồn quantity và price
            OrderItem orderItem = existingOrderItem.get();
            orderItem.setQuantity(orderItem.getQuantity() + subItem.getQuantity());
            orderItem.setPrice(orderItem.getPrice().add(subItem.getPrice()));
        } else {
            // Nếu chưa tồn tại, tạo mới OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(subItem.getMenuItem());
            orderItem.setQuantity(subItem.getQuantity());
            orderItem.setPrice(subItem.getPrice());
            order.getOrderItems().add(orderItem);
        }
    }

    // Cập nhật tổng giá tiền của Order chính
    BigDecimal totalSubOrderPrice = subOrder.getTotalPrice();
    order.setTotalPrice(order.getTotalPrice().add(totalSubOrderPrice));

    // Đặt trạng thái SubOrder thành CONFIRMED
    subOrder.setStatus(OrderStatus.CONFIRMED);

    // Lưu SubOrder trước
    subOrderRepository.save(subOrder);

    // Log để kiểm tra giá trị trước khi lưu
    log.info("Before saving Order: createdAt={}, updatedAt={}", order.getCreatedAt(), order.getUpdatedAt());

    // Lưu Order
    Order updatedOrder = orderRepository.save(order);

    // Log để kiểm tra giá trị sau khi lưu
    log.info("After saving Order: createdAt={}, updatedAt={}", updatedOrder.getCreatedAt(), updatedOrder.getUpdatedAt());

    return updatedOrder;
}
}
