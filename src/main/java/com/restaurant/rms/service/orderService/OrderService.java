//package com.restaurant.rms.service.orderService;
//
//import com.restaurant.rms.dto.request.orderDTO.OrderDTO;
//import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
//import com.restaurant.rms.dto.request.orderDTO.SubOrderItemDTO;
//import com.restaurant.rms.entity.DiningTable;
//import com.restaurant.rms.entity.Order;
//import com.restaurant.rms.entity.SubOrder;
//import com.restaurant.rms.enums.DiningTableStatus;
//import com.restaurant.rms.enums.OrderStatus;
//
//import com.restaurant.rms.mapper.orderMapper.OrderMapper;
//import com.restaurant.rms.mapper.orderMapper.SubOrderMapper;
//import com.restaurant.rms.repository.DiningTableRepository;
//import com.restaurant.rms.repository.OrderRepository;
//import com.restaurant.rms.repository.SubOrderRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class OrderService {
//    private final OrderRepository orderRepository;
//    private final SubOrderRepository subOrderRepository;
//    private final DiningTableRepository diningTableRepository;
//    private final OrderMapper orderMapper;
//    private final SubOrderMapper subOrderMapper;
//
//    @Transactional
//    public OrderDTO createOrderOrSubOrder(OrderDTO orderDTO) {
//        // 🔍 Debug giá trị orderDTO nhận vào
//        log.info("📥 Nhận request tạo order: {}", orderDTO);
//
//        // 🔍 Kiểm tra từng OrderItem xem có bị null không
//        orderDTO.getOrderItems().forEach(orderItem ->
//                log.info("🔍 Kiểm tra OrderItem: menuItemId={}, quantity={}, price={}",
//                        orderItem.getMenuItemId(), orderItem.getQuantity(), orderItem.getPrice())
//        );
//        // Kiểm tra xem đã có Order chưa
//        Optional<Order> existingOrder = orderRepository.findByDiningTable_DiningTableIdAndStatus(
//                orderDTO.getDiningTableId(), OrderStatus.PENDING);
//
//        if (existingOrder.isPresent()) {
//            // Nếu đã có Order, tạo SubOrder
//            SubOrderDTO subOrderDTO = new SubOrderDTO();
//            subOrderDTO.setOrderId(existingOrder.get().getOrderId());
//            subOrderDTO.setStatus(OrderStatus.PENDING.name());
//            subOrderDTO.setTotalPrice(orderDTO.getTotalPrice());
//
//            // Chuyển đổi danh sách OrderItemDTO -> SubOrderItemDTO
//            List<SubOrderItemDTO> subOrderItems = orderDTO.getOrderItems().stream()
//                    .map(orderItem -> SubOrderItemDTO.builder()
//                            .menuItemId(orderItem.getMenuItemId())
//                            .quantity(orderItem.getQuantity())
//                            .price(orderItem.getPrice())
//                            .build())
//                    .collect(Collectors.toList());
//
//            subOrderDTO.setSubOrderItems(subOrderItems);
//
//            createSubOrder(subOrderDTO);
//            return orderMapper.toDTO(existingOrder.get());
//        } else {
//            // Nếu chưa có Order, tạo Order mới
//            DiningTable table = diningTableRepository.findById(orderDTO.getDiningTableId())
//                    .orElseThrow(() -> new RuntimeException("Dining table not found"));
//
//            Order order = orderMapper.toEntity(orderDTO, table);
//            order.setStatus(OrderStatus.PENDING);
//
//            // Chuyển trạng thái bàn thành OCCUPIED
//            table.setStatus(DiningTableStatus.OCCUPIED);
//            diningTableRepository.save(table);
//
//            Order savedOrder = orderRepository.save(order);
//            return orderMapper.toDTO(savedOrder);
//        }
//    }
//
//
//    @Transactional
//    public void completeOrder(int orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        order.setStatus(OrderStatus.COMPLETED);
//
//        // Chuyển trạng thái bàn thành AVAILABLE
//        DiningTable table = order.getDiningTable();
//        table.setStatus(DiningTableStatus.AVAILABLE);
//        diningTableRepository.save(table);
//
//        orderRepository.save(order);
//    }
//
//    @Transactional
//    public void createSubOrder(SubOrderDTO subOrderDTO) {
//        SubOrder subOrder = subOrderMapper.toEntity(subOrderDTO, orderRepository.findById(subOrderDTO.getOrderId())
//                .orElseThrow(() -> new RuntimeException("Order not found")));
//        subOrder.setStatus(OrderStatus.PENDING);
//
//        subOrderRepository.save(subOrder);
//    }
//}
