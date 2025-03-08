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


    @Transactional
    public void confirmSubOrder(int subOrderId) {
        SubOrder subOrder = subOrderRepository.findById(subOrderId)
                .orElseThrow(() -> new RuntimeException("SubOrder not found"));

        Order order = subOrder.getOrder();

        // Cộng dồn món ăn từ SubOrder vào Order chính
        List<SubOrderItem> subOrderItems = subOrder.getSubOrderItems();
        for (SubOrderItem subItem : subOrderItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(subItem.getMenuItem());
            orderItem.setQuantity(subItem.getQuantity());
            orderItem.setPrice(subItem.getPrice());
            order.getOrderItems().add(orderItem);
        }

        // Cập nhật tổng giá tiền của Order chính
        BigDecimal totalSubOrderPrice = subOrder.getTotalPrice();
        order.setTotalPrice(order.getTotalPrice().add(totalSubOrderPrice));

        subOrder.setStatus(OrderStatus.CONFIRMED);
        subOrderRepository.save(subOrder);
        orderRepository.save(order);
    }
}
