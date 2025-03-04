//package com.restaurant.rms.service.orderService;
//
//import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
//import com.restaurant.rms.entity.Order;
//import com.restaurant.rms.entity.SubOrder;
//import com.restaurant.rms.entity.SubOrderItem;
//import com.restaurant.rms.entity.OrderItem;
//import com.restaurant.rms.enums.OrderStatus;
//import com.restaurant.rms.mapper.orderMapper.SubOrderMapper;
//import com.restaurant.rms.repository.OrderRepository;
//import com.restaurant.rms.repository.SubOrderRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class SubOrderService {
//    private final SubOrderRepository subOrderRepository;
//    private final OrderRepository orderRepository;
//    private final SubOrderMapper subOrderMapper;
//
//    @Transactional
//    public SubOrderDTO createSubOrder(SubOrderDTO subOrderDTO) {
//        Order order = orderRepository.findById(subOrderDTO.getOrderId())
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        SubOrder subOrder = subOrderMapper.toEntity(subOrderDTO, order);
//        subOrder.setStatus(OrderStatus.PENDING);
//
//        SubOrder savedSubOrder = subOrderRepository.save(subOrder);
//        return subOrderMapper.toDTO(savedSubOrder);
//    }
//
//    @Transactional
//    public void completeSubOrder(int subOrderId) {
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
//        subOrder.setStatus(OrderStatus.COMPLETED);
//        subOrderRepository.save(subOrder);
//        orderRepository.save(order);
//    }
//}
