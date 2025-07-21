//package com.restaurant.rms.service.orderService;
//
//import com.restaurant.rms.dto.request.orderDTO.OrderDTO;
//import com.restaurant.rms.dto.request.orderDTO.OrderItemDTO;
//import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
//import com.restaurant.rms.dto.request.orderDTO.SubOrderItemDTO;
//import com.restaurant.rms.entity.DiningTable;
//import com.restaurant.rms.entity.Order;
//import com.restaurant.rms.entity.SubOrder;
//import com.restaurant.rms.entity.SubOrderItem;
//import com.restaurant.rms.enums.DiningTableStatus;
//import com.restaurant.rms.enums.OrderStatus;
//
//import com.restaurant.rms.mapper.orderMapper.OrderMapper;
//import com.restaurant.rms.mapper.orderMapper.SubOrderMapper;
//import com.restaurant.rms.repository.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
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
//    private final SubOrderService subOrderService;
//    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
//    private final SubOrderItemRepository subOrderItemRepository;
//
//    public boolean hasExistingOrder(int diningTableId) {
//        return orderRepository.existsByDiningTable_DiningTableIdAndStatusIn(
//                diningTableId, Arrays.asList(OrderStatus.PENDING, OrderStatus.CONFIRMED));
//    }
//
//
//    @Transactional
//    public Object createOrderOrSubOrder(OrderDTO orderDTO) {
//        log.info("📥 Nhận request tạo order: {}", orderDTO);
//
//        boolean hasExistingOrder = hasExistingOrder(orderDTO.getDiningTableId());
//
//        if (hasExistingOrder) {
//            // 🔄 Chuyển đổi OrderDTO thành SubOrderDTO
//            Order existingOrder = orderRepository.findByDiningTable_DiningTableIdAndStatus(orderDTO.getDiningTableId(), OrderStatus.PENDING)
//                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Order đang chờ"));
//
//            SubOrderDTO subOrderDTO = new SubOrderDTO();
//            subOrderDTO.setOrderId(existingOrder.getOrderId());
//            subOrderDTO.setStatus(OrderStatus.PENDING.name());
//            subOrderDTO.setTotalPrice(orderDTO.getTotalPrice());
//
//            // 🔄 Chuyển đổi danh sách OrderItemDTO thành SubOrderItemDTO
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
//            return createSubOrder(subOrderDTO); // ✅ Gọi phương thức với đúng tham số
//        } else {
//            return createNewOrder(orderDTO);
//        }
//    }
//
//    private SubOrderDTO createSubOrder(SubOrderDTO subOrderDTO) {
//        // Lấy Order cha từ orderId của SubOrderDTO
//        Order parentOrder = orderRepository.findById(subOrderDTO.getOrderId())
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy Order cha với ID: " + subOrderDTO.getOrderId()));
//
//        // Tạo SubOrder mới
//        SubOrder subOrder = new SubOrder();
//        subOrder.setOrder(parentOrder);
//        subOrder.setStatus(OrderStatus.PENDING);
//        subOrder.setTotalPrice(subOrderDTO.getTotalPrice());
//
//        SubOrder savedSubOrder = subOrderRepository.save(subOrder); // Lưu SubOrder vào DB
//
//        // Chuyển đổi SubOrderItemDTO -> SubOrderItem entity
//        List<SubOrderItem> subOrderItems = subOrderDTO.getSubOrderItems().stream()
//                .map(itemDTO -> new SubOrderItem(
//                        0,  // ID tự động tạo
//                        savedSubOrder,  // Gán SubOrder đã lưu
//                        restaurantMenuItemRepository.findById(itemDTO.getMenuItemId())
//                                .orElseThrow(() -> new RuntimeException("Không tìm thấy MenuItem với ID: " + itemDTO.getMenuItemId())),
//                        itemDTO.getQuantity(),
//                        itemDTO.getPrice()
//                )).collect(Collectors.toList());
//
//        subOrderItemRepository.saveAll(subOrderItems); // Lưu SubOrderItems vào DB
//
//        savedSubOrder.setSubOrderItems(subOrderItems);
//
//        return subOrderMapper.toDTO(savedSubOrder);
//    }
//
//
//    private OrderDTO createNewOrder(OrderDTO orderDTO) {
//        DiningTable table = diningTableRepository.findById(orderDTO.getDiningTableId())
//                .orElseThrow(() -> new RuntimeException("Dining table not found"));
//
//        Order order = orderMapper.toEntity(orderDTO, table);
//        order.setStatus(OrderStatus.PENDING);
//
//        table.setStatus(DiningTableStatus.OCCUPIED);
//        diningTableRepository.save(table);
//
//        Order savedOrder = orderRepository.save(order);
//        return orderMapper.toDTO(savedOrder);
//    }
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
////    @Transactional
////    public OrderDTO createOrderOrSubOrder(OrderDTO orderDTO) {
////        // 🔍 Debug giá trị orderDTO nhận vào
////        log.info("📥 Nhận request tạo order: {}", orderDTO);
////
////        // 🔍 Kiểm tra từng OrderItem xem có bị null không
////        orderDTO.getOrderItems().forEach(orderItem ->
////                log.info("🔍 Kiểm tra OrderItem: menuItemId={}, quantity={}, price={}",
////                        orderItem.getMenuItemId(), orderItem.getQuantity(), orderItem.getPrice())
////        );
////        // Kiểm tra xem đã có Order chưa
////        Optional<Order> existingOrder = orderRepository.findByDiningTable_DiningTableIdAndStatus(
////                orderDTO.getDiningTableId(), OrderStatus.PENDING);
////
////        if (existingOrder.isPresent()) {
////            // Nếu đã có Order, tạo SubOrder
////            SubOrderDTO subOrderDTO = new SubOrderDTO();
////            subOrderDTO.setOrderId(existingOrder.get().getOrderId());
////            subOrderDTO.setStatus(OrderStatus.PENDING.name());
////            subOrderDTO.setTotalPrice(orderDTO.getTotalPrice());
////
////            // Chuyển đổi danh sách OrderItemDTO -> SubOrderItemDTO
////            List<SubOrderItemDTO> subOrderItems = orderDTO.getOrderItems().stream()
////                    .map(orderItem -> SubOrderItemDTO.builder()
////                            .menuItemId(orderItem.getMenuItemId())
////                            .quantity(orderItem.getQuantity())
////                            .price(orderItem.getPrice())
////                            .build())
////                    .collect(Collectors.toList());
////
////            subOrderDTO.setSubOrderItems(subOrderItems);
////
////            createSubOrder(subOrderDTO);
////            return orderMapper.toDTO(existingOrder.get());
////        } else {
////            // Nếu chưa có Order, tạo Order mới
////            DiningTable table = diningTableRepository.findById(orderDTO.getDiningTableId())
////                    .orElseThrow(() -> new RuntimeException("Dining table not found"));
////
////            Order order = orderMapper.toEntity(orderDTO, table);
////            order.setStatus(OrderStatus.PENDING);
////
////            // Chuyển trạng thái bàn thành OCCUPIED
////            table.setStatus(DiningTableStatus.OCCUPIED);
////            diningTableRepository.save(table);
////
////            Order savedOrder = orderRepository.save(order);
////            return orderMapper.toDTO(savedOrder);
////        }
////    }
//
//
//
//
////    @Transactional
////    public void createSubOrder(SubOrderDTO subOrderDTO) {
////        SubOrder subOrder = subOrderMapper.toEntity(subOrderDTO, orderRepository.findById(subOrderDTO.getOrderId())
////                .orElseThrow(() -> new RuntimeException("Order not found")));
////        subOrder.setStatus(OrderStatus.PENDING);
////
////        subOrderRepository.save(subOrder);
////    }
//}

package com.restaurant.rms.service.orderService;

import com.restaurant.rms.dto.request.RechargeRequestDTO;
import com.restaurant.rms.dto.request.orderDTO.*;
import com.restaurant.rms.entity.*;
import com.restaurant.rms.enums.DiningTableStatus;
import com.restaurant.rms.enums.OrderStatus;
import com.restaurant.rms.enums.PaymentMethod;
import com.restaurant.rms.enums.PaymentStatus;
import com.restaurant.rms.mapper.orderMapper.OrderItemMapper;
import com.restaurant.rms.mapper.orderMapper.OrderMapper;
import com.restaurant.rms.mapper.orderMapper.SubOrderMapper;
import com.restaurant.rms.repository.*;
import com.restaurant.rms.service.PaymentService;
//import com.restaurant.rms.service.notificationService.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final SubOrderRepository subOrderRepository;
    private final DiningTableRepository diningTableRepository;
    private final OrderMapper orderMapper;
    private final SubOrderMapper subOrderMapper;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
    private final SubOrderItemRepository subOrderItemRepository;
    private final OrderItemMapper orderItemMapper;
//    private final NotificationService notificationService;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    public boolean hasExistingOrder(int diningTableId) {
        return orderRepository.existsByDiningTable_DiningTableIdAndStatusIn(
                diningTableId, Arrays.asList(OrderStatus.PENDING, OrderStatus.CONFIRMED));
    }

    @Transactional
    public Object createOrderOrSubOrder(OrderDTO orderDTO) {
        log.info("📥 Nhận request tạo order: {}", orderDTO);

        boolean hasExistingOrder = hasExistingOrder(orderDTO.getDiningTableId());

        if (hasExistingOrder) {
            Order existingOrder = orderRepository.findByDiningTable_DiningTableIdAndStatus(orderDTO.getDiningTableId(), OrderStatus.PENDING)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy Order đang chờ"));

            SubOrderDTO subOrderDTO = new SubOrderDTO();
            subOrderDTO.setOrderId(existingOrder.getOrderId());
            subOrderDTO.setStatus(OrderStatus.PENDING.name());
            subOrderDTO.setTotalPrice(orderDTO.getTotalPrice());

            List<SubOrderItemDTO> subOrderItems = orderDTO.getOrderItems().stream()
                    .map(orderItem -> SubOrderItemDTO.builder()
                            .menuItemId(orderItem.getMenuItemId())
                            .quantity(orderItem.getQuantity())
                            .price(orderItem.getPrice())
                            .build())
                    .collect(Collectors.toList());

            subOrderDTO.setSubOrderItems(subOrderItems);

            return createSubOrder(subOrderDTO);
        } else {
            return createNewOrder(orderDTO);
        }
    }

    private SubOrderDTO createSubOrder(SubOrderDTO subOrderDTO) {
        Order parentOrder = orderRepository.findById(subOrderDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Order cha với ID: " + subOrderDTO.getOrderId()));

        SubOrder subOrder = new SubOrder();
        subOrder.setOrder(parentOrder);
        subOrder.setStatus(OrderStatus.PENDING);
        subOrder.setTotalPrice(subOrderDTO.getTotalPrice());

        SubOrder savedSubOrder = subOrderRepository.save(subOrder);

        List<SubOrderItem> subOrderItems = subOrderDTO.getSubOrderItems().stream()
                .map(itemDTO -> new SubOrderItem(
                        0,
                        savedSubOrder,
                        restaurantMenuItemRepository.findById(itemDTO.getMenuItemId())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy MenuItem với ID: " + itemDTO.getMenuItemId())),
                        itemDTO.getQuantity(),
                        itemDTO.getPrice()
                )).collect(Collectors.toList());

        subOrderItemRepository.saveAll(subOrderItems);

        savedSubOrder.setSubOrderItems(subOrderItems);

        return subOrderMapper.toDTO(savedSubOrder);
    }

    private OrderDTO createNewOrder(OrderDTO orderDTO) {
        DiningTable table = diningTableRepository.findById(orderDTO.getDiningTableId())
                .orElseThrow(() -> new RuntimeException("Dining table not found"));

        Order order = orderMapper.toEntity(orderDTO, table);
        order.setStatus(OrderStatus.PENDING);

        table.setStatus(DiningTableStatus.OCCUPIED);
        diningTableRepository.save(table);

        Order savedOrder = orderRepository.save(order);

        // Log để kiểm tra createdAt sau khi lưu
        log.info("After saving new Order: createdAt={}, updatedAt={}", savedOrder.getCreatedAt(), savedOrder.getUpdatedAt());

        return orderMapper.toDTO(savedOrder);
    }

//    @Transactional
//    public void completeOrder(int orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        order.setStatus(OrderStatus.COMPLETED);
//
//        DiningTable table = order.getDiningTable();
//        table.setStatus(DiningTableStatus.AVAILABLE);
//
//        diningTableRepository.save(table);
//
//        orderRepository.save(order);
//    }
@Transactional
public void completeOrder(int orderId) {
    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));

    order.setStatus(OrderStatus.COMPLETED);

    DiningTable table = order.getDiningTable();
    table.setStatus(DiningTableStatus.AVAILABLE);

    diningTableRepository.save(table);
    orderRepository.save(order);

    // Gửi thông báo sau khi hoàn tất đơn hàng
//    sendCompleteOrderNotification(orderId);
}

    @Transactional
    public void completeOrderWithCash(int orderId) {
        // 1. Lấy thông tin đơn hàng
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 2. Kiểm tra trạng thái đơn hàng (Tránh hoàn tất nhiều lần)
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Order đã được hoàn tất.");
        }


        // 4. Cập nhật trạng thái đơn hàng thành COMPLETED
        order.setStatus(OrderStatus.COMPLETED);

        // 5. Cập nhật trạng thái bàn ăn thành AVAILABLE
        DiningTable table = order.getDiningTable();
        table.setStatus(DiningTableStatus.AVAILABLE);
        diningTableRepository.save(table);
        orderRepository.save(order);

        // 6. Lưu thông tin thanh toán bằng CASH
        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethod.CASH);
        payment.setPaymentStatus(PaymentStatus.PAID);
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaidAt(LocalDateTime.now());
        payment.setOrder(order);
        paymentRepository.save(payment);

        // 7. Gửi thông báo sau khi hoàn tất đơn hàng
//        sendCompleteOrderNotification(orderId);

        System.out.println(" Đơn hàng " + orderId + " đã hoàn tất thanh toán tiền mặt.");
    }


//    // Phương thức gửi thông báo khi hoàn tất đơn hàng
//    private void sendCompleteOrderNotification(int orderId) {
//        String title = "Cập nhật doanh thu";
//        String body = "Doanh thu ngày đã được cập nhật sau khi hoàn tất đơn hàng #" + orderId;
//
//        try {
//            NotificationEntity notification = notificationService.sendNotification(
//                    "1", // Gửi tới userId cố định (có thể thay đổi theo logic)
//                    title,
//                    body
//            );
//            log.info("Gửi thông báo hoàn tất đơn hàng thành công: orderId={}", orderId);
//        } catch (Exception e) {
//            log.error("Lỗi khi gửi thông báo hoàn tất đơn hàng: orderId={}, error={}", orderId, e.getMessage(), e);
//        }
//    }

        public OrderDTO getCompletedOrderReceipt(Integer orderId) {
        Order order = orderRepository.findCompletedOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn hoàn thành cho Order ID: " + orderId));

        return OrderDTO.builder()
                .id(order.getOrderId())
                .diningTableId(order.getDiningTable().getDiningTableId())
                .status(order.getStatus().toString())
                .totalPrice(order.getTotalPrice())
                .orderItems(order.getOrderItems().stream().map(item ->
                        OrderItemDTO.builder()
                                .id(item.getOrderItemId())
                                .menuItemId(item.getMenuItem().getRestaurantMenuItemId())
                                .menuItemName(item.getMenuItem().getFood().getName())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }


    //  Doanh thu **tất cả nhà hàng**
    public BigDecimal getTotalRevenueByDay(int year, int month, int day) {
        return orderRepository.getTotalRevenueByDay(year, month, day);
    }
    public BigDecimal getTotalRevenueByMonth(int year, int month) {
        return orderRepository.getTotalRevenueByMonth(year, month);
    }
    public BigDecimal getTotalRevenueByYear(int year) {
        return orderRepository.getTotalRevenueByYear(year);
    }

    //  Doanh thu **từng nhà hàng**
    public BigDecimal getRestaurantRevenueByDay(int restaurantId, int year, int month, int day) {
        return orderRepository.getRestaurantRevenueByDay(restaurantId, year, month, day);
    }
    public BigDecimal getRestaurantRevenueByMonth(int restaurantId, int year, int month) {
        return orderRepository.getRestaurantRevenueByMonth(restaurantId, year, month);
    }
    public BigDecimal getRestaurantRevenueByYear(int restaurantId, int year) {
        return orderRepository.getRestaurantRevenueByYear(restaurantId, year);
    }

    //  Doanh thu **tất cả nhà hàng** từ ngày đến ngày
    public BigDecimal getTotalRevenueBetweenDates(LocalDate startDate, LocalDate endDate) {
        return orderRepository.getTotalRevenueBetweenDates(startDate, endDate);
    }

    //  Doanh thu **từng nhà hàng** từ ngày đến ngày
    public BigDecimal getRestaurantRevenueBetweenDates(int restaurantId, LocalDate startDate, LocalDate endDate) {
        return orderRepository.getRestaurantRevenueBetweenDates(restaurantId, startDate, endDate);
    }

    // Thêm phương thức: Lấy Order theo ID
    public OrderDTO getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        return orderMapper.toDTO(order);
    }

    // Thêm phương thức: Xóa Order (xóa vật lý hoặc xóa mềm tùy cấu hình)
    @Transactional
    public void deleteOrder(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        // Giả định xóa vật lý, nếu dùng xóa mềm thì thêm trường isDeleted
        orderRepository.delete(order);
        DiningTable table = order.getDiningTable();
        if (table != null && order.getStatus() != OrderStatus.COMPLETED) {
            table.setStatus(DiningTableStatus.AVAILABLE);
            diningTableRepository.save(table);
        }
    }

    @Transactional
    public OrderDTO updateOrder(int orderId, OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Kiểm tra nếu Order đã COMPLETED thì không cho sửa
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new RuntimeException("Cannot update Order with ID: " + orderId + " because it is already COMPLETED");
        }

        // Cập nhật trạng thái nếu có
        if (orderDTO.getStatus() != null) {
            order.setStatus(OrderStatus.valueOf(orderDTO.getStatus()));
        }

        // Cập nhật totalPrice nếu có
        if (orderDTO.getTotalPrice() != null) {
            order.setTotalPrice(orderDTO.getTotalPrice());
        }

        // Cập nhật danh sách OrderItem nếu có
        if (orderDTO.getOrderItems() != null && !orderDTO.getOrderItems().isEmpty()) {
            order.getOrderItems().clear();
            order.getOrderItems().addAll(orderDTO.getOrderItems().stream()
                    .map(itemDTO -> orderItemMapper.toEntity(itemDTO, order,
                            restaurantMenuItemRepository.findById(itemDTO.getMenuItemId())
                                    .orElseThrow(() -> new RuntimeException("MenuItem not found: " + itemDTO.getMenuItemId()))))
                    .collect(Collectors.toList()));
        }

        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }

    // Thêm phương thức: Lấy tất cả Order
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAllOrdersWithActiveDiningTables();
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public OrderItemDTO updateOrderItem(int orderId, int orderItemId, UpdateOrderItemDTO updateOrderItemDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Không cho cập nhật nếu Order đã hoàn thành
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new RuntimeException("Cannot update OrderItem for Order ID: " + orderId + " because it is already COMPLETED");
        }

        OrderItem orderItem = order.getOrderItems().stream()
                .filter(item -> item.getOrderItemId() == orderItemId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("OrderItem not found with ID: " + orderItemId));

        // Cập nhật số lượng
        if (updateOrderItemDTO.getQuantity() > 0) {
            orderItem.setQuantity(updateOrderItemDTO.getQuantity());
        }

        // Tính tổng tiền mới cho Order
        BigDecimal newTotalPrice = calculateTotalPrice(order);

        // Cập nhật totalPrice cho Order
        order.setTotalPrice(newTotalPrice);
        orderRepository.save(order);

        return orderItemMapper.toDTO(orderItem);
    }

    // Hàm tính tổng tiền của Order dựa trên các OrderItem
    private BigDecimal calculateTotalPrice(Order order) {
        return order.getOrderItems().stream()
                .map(item -> item.getMenuItem().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    // Thêm phương thức: Lấy Order theo DiningTable
    @Transactional(readOnly = true)
    public OrderDTO getOrderByDiningTable(int diningTableId) {
        Order order = orderRepository.findActiveOrderByDiningTableId(diningTableId)
                .orElseThrow(() -> new RuntimeException("No active Order found for DiningTable ID: " + diningTableId));
        return orderMapper.toDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByRestaurantId(int restaurantId) {
        List<Order> orders = orderRepository.findByDiningTable_Restaurant_RestaurantId(restaurantId);
        if (orders.isEmpty()) {
            throw new RuntimeException("No Orders found for Restaurant ID: " + restaurantId);
        }
        return orders.stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }
}
