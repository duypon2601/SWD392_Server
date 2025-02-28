//package com.restaurant.rms.service.orderService;
//
//import com.restaurant.rms.dto.request.orderDTO.OrderDTO;
//import com.restaurant.rms.dto.request.orderDTO.OrderItemDTO;
//import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
//import com.restaurant.rms.entity.*;
//import com.restaurant.rms.enums.DiningTableStatus;
//import com.restaurant.rms.enums.OrderStatus;
//import com.restaurant.rms.mapper.orderMapper.OrderMapper;
//import com.restaurant.rms.mapper.orderMapper.SubOrderMapper;
//import com.restaurant.rms.repository.*;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class OrderServiceImpl implements OrderService {
//    private final OrderRepository orderRepository;
//    private final SubOrderRepository subOrderRepository;
//    private final SubOrderItemRepository subOrderItemRepository;
//    private final RestaurantMenuItemRepository menuItemRepository;
//    private final DiningTableRepository diningTableRepository;
//    private final OrderItemRepository orderItemRepository;
//
//    @Override
//    @Transactional
//    public OrderDTO createOrder(int tableId, List<OrderItemDTO> orderItemDTOs) {
//        DiningTable table = diningTableRepository.findById(tableId)
//                .orElseThrow(() -> new RuntimeException("Dining table not found"));
//
//        // Tạo Order mới
//        Order order = new Order();
//        order.setDiningTable(table);
//        order.setStatus(OrderStatus.PENDING);
//
//        // Lưu order trước để có ID
//        order = orderRepository.save(order);
//
//        // Chuyển DTO thành Entity
//        List<OrderItem> orderItems = orderItemDTOs.stream().map(dto -> {
//            RestaurantMenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
//                    .orElseThrow(() -> new RuntimeException("Menu item not found"));
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            orderItem.setMenuItem(menuItem);
//            orderItem.setQuantity(dto.getQuantity());
//            orderItem.setPrice(menuItem.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));
//
//            return orderItem;
//        }).toList();
//
//        // Tính tổng giá
//        BigDecimal totalPrice = orderItems.stream()
//                .map(OrderItem::getPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        order.setTotalPrice(totalPrice);
//        orderRepository.save(order);
//        orderItemRepository.saveAll(orderItems);
//
//        // Cập nhật trạng thái bàn
//        table.setStatus(DiningTableStatus.OCCUPIED);
//        diningTableRepository.save(table);
//
//        return OrderMapper.toDTO(order);
//    }
//
//    @Override
//    @Transactional
//    public void processOrder(int diningTableId, List<OrderItemDTO> orderItemDTOs) {
//        DiningTable table = diningTableRepository.findById(diningTableId)
//                .orElseThrow(() -> new RuntimeException("Dining table not found"));
//
//        // Kiểm tra bàn đã có Order chưa
//        orderRepository.findByDiningTableAndStatus(table, OrderStatus.PENDING)
//                .ifPresentOrElse(
//                        existingOrder -> createSubOrder(existingOrder.getId(), orderItemDTOs),
//                        () -> createOrder(diningTableId, orderItemDTOs)
//                );
//
//        // Cập nhật trạng thái bàn
//        table.setStatus(DiningTableStatus.OCCUPIED);
//        diningTableRepository.save(table);
//    }
//
//    @Transactional
//    public SubOrderDTO createSubOrder(int orderId, List<OrderItemDTO> orderItemDTOs) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        // Tạo subOrder mới
//        SubOrder subOrder = new SubOrder();
//        subOrder.setOrder(order);
//        subOrder.setStatus(OrderStatus.PENDING);
//
//        // Chuyển DTO thành Entity
//        List<SubOrderItem> subOrderItems = orderItemDTOs.stream().map(dto -> {
//            RestaurantMenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
//                    .orElseThrow(() -> new RuntimeException("Menu item not found"));
//
//            SubOrderItem subOrderItem = new SubOrderItem();
//            subOrderItem.setSubOrder(subOrder);
//            subOrderItem.setMenuItem(menuItem);
//            subOrderItem.setQuantity(dto.getQuantity());
//            subOrderItem.setPrice(menuItem.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity())));
//
//            return subOrderItem;
//        }).collect(Collectors.toList());
//
//        // Tính tổng giá
//        BigDecimal totalPrice = subOrderItems.stream()
//                .map(SubOrderItem::getPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        subOrder.setTotalPrice(totalPrice);
//        subOrder = subOrderRepository.save(subOrder);
//        subOrderItemRepository.saveAll(subOrderItems);
//
//        return SubOrderMapper.toDTO(subOrder);
//    }
//
//    @Override
//    @Transactional
//    public void completeSubOrder(int subOrderId) {
//        SubOrder subOrder = subOrderRepository.findById(subOrderId)
//                .orElseThrow(() -> new RuntimeException("SubOrder not found"));
//
//        Order order = subOrder.getOrder();
//
//        // Chuyển các món ăn từ subOrder vào order chính
//        List<OrderItem> orderItems = subOrder.getSubOrderItems().stream().map(subOrderItem -> {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrder(order);
//            orderItem.setMenuItem(subOrderItem.getMenuItem());
//            orderItem.setQuantity(subOrderItem.getQuantity());
//            orderItem.setPrice(subOrderItem.getPrice());
//            return orderItem;
//        }).collect(Collectors.toList());
//
//        // Cập nhật đơn hàng chính
//        order.getOrderItems().addAll(orderItems);
//        order.setTotalPrice(order.getTotalPrice().add(subOrder.getTotalPrice()));
//
//        // Đánh dấu subOrder hoàn thành
//        subOrder.setStatus(OrderStatus.COMPLETED);
//
//        orderRepository.save(order);
//        subOrderRepository.save(subOrder);
//    }
//
//    @Override
//    public Optional<OrderDTO> getOrderByTable(int diningTableId) {
//        return orderRepository.findByDiningTableIdAndStatus(diningTableId, OrderStatus.PENDING)
//                .map(OrderMapper::toDTO);
//    }
//
//    @Override
//    public Optional<SubOrderDTO> getSubOrderById(int subOrderId) {
//        return subOrderRepository.findById(subOrderId)
//                .map(SubOrderMapper::toDTO);
//    }
//}
