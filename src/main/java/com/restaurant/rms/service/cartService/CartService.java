//package com.restaurant.rms.service.cartService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.restaurant.rms.dto.request.CartItemDTO;
//import com.restaurant.rms.dto.request.CheckoutCartDTO;
//import com.restaurant.rms.dto.request.orderDTO.OrderDTO;
//import com.restaurant.rms.dto.request.orderDTO.OrderItemDTO;
//import com.restaurant.rms.entity.DiningTable;
//import com.restaurant.rms.enums.OrderStatus;
//import com.restaurant.rms.repository.DiningTableRepository;
//import com.restaurant.rms.service.orderService.OrderService;
//import com.restaurant.rms.util.RedisUtil;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class CartService {
//
//    private final RedisUtil redisUtil;
//    private final DiningTableRepository diningTableRepository;
//    private final OrderService orderService;
//
//    private String getCartKey(String tableQr) {
//        return "cart:" + tableQr;
//    }
//
//    public List<CartItemDTO> getCart(String tableQr) {
//        return redisUtil.getCart(getCartKey(tableQr));
//    }
//
//    public void addToCart(String tableQr, CartItemDTO cartItem) {
//        if (cartItem.getMenuItemId() == null) {
//            throw new RuntimeException("🚨 menuItemId không thể null khi thêm vào giỏ!");
//        }
//        log.info("➕ Thêm món vào giỏ: {}", cartItem);
//        redisUtil.addToCart(getCartKey(tableQr), cartItem);
//    }
//
//    public void updateCart(String tableQr, List<CartItemDTO> updatedCart) {
//        log.info("🔄 Cập nhật giỏ hàng: {}", updatedCart);
//        redisUtil.updateCart(getCartKey(tableQr), updatedCart);
//    }
//
//    public void removeItemFromCart(String tableQr, int menuItemId) {
//        log.info("❌ Xóa món {} khỏi giỏ hàng", menuItemId);
//        redisUtil.removeItemFromCart(getCartKey(tableQr), menuItemId);
//    }
//
//    public void clearCart(String tableQr) {
//        log.info("🗑 Xóa toàn bộ giỏ hàng của bàn {}", tableQr);
//        redisUtil.clearCart(getCartKey(tableQr));
//    }
//
//    @Transactional
//    public CheckoutCartDTO checkoutAndCreateOrder(String tableQr) throws JsonProcessingException {
//        log.info("🛒 Bắt đầu checkout cho bàn: {}", tableQr);
//
//        String key = getCartKey(tableQr);
//
//        // Lấy danh sách món trong giỏ hàng (CartItemDTO)
//        List<CartItemDTO> cartItems = getCart(tableQr);
//        log.info("📜 Danh sách món trong giỏ: {}", cartItems);
//
//        if (cartItems.isEmpty()) {
//            log.error("🚨 Giỏ hàng trống!");
//            throw new RuntimeException("Giỏ hàng trống!");
//        }
//
//        // Lấy thông tin bàn ăn từ QR
//        DiningTable table = diningTableRepository.findByQrCode(tableQr)
//                .orElseThrow(() -> {
//                    log.error("🚨 Không tìm thấy bàn ăn với QR: {}", tableQr);
//                    return new RuntimeException("Bàn không tồn tại!");
//                });
//
//        log.info("🍽 Bàn ăn: {}", table);
//
//        // 🔥 Chuyển đổi CartItemDTO → OrderItemDTO
//        List<OrderItemDTO> orderItemDTOs = cartItems.stream()
//                .map(cartItem -> {
//                    // Debug xem menuItemId có null không
//                    if (cartItem.getMenuItemId() == null) {
//                        throw new RuntimeException("🚨 menuItemId bị null cho cartItem: " + cartItem);
//                    }
//                    return OrderItemDTO.builder()
//                            .menuItemId(cartItem.getMenuItemId())
//                            .quantity(cartItem.getQuantity())
//                            .price(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
//                            .build();
//                }).collect(Collectors.toList());
//
//
//        // Tính tổng tiền
//        BigDecimal totalPrice = orderItemDTOs.stream()
//                .map(OrderItemDTO::getPrice)  // Lấy giá từ OrderItemDTO
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
//            throw new RuntimeException("🚨 Lỗi: Tổng tiền không hợp lệ!");
//        }
//
//        log.info("💰 Tổng tiền đơn hàng: {}", totalPrice);
//
//        // 🔥 Tạo OrderDTO từ danh sách OrderItemDTO
//        OrderDTO orderDTO = OrderDTO.builder()
//                .diningTableId(table.getDiningTableId())
//                .status(OrderStatus.PENDING.name())
//                .totalPrice(totalPrice)
//                .orderItems(orderItemDTOs)  // Đúng kiểu dữ liệu
//                .build();
//
//        log.info("📦 Tạo đơn hàng với thông tin: {}", orderDTO);
//
//        // Gọi OrderService để tạo Order/SubOrder
//        OrderDTO createdOrder = orderService.createOrderOrSubOrder(orderDTO);
//
//        log.info("✅ Đơn hàng đã tạo: {}", createdOrder);
//
//        // Xóa giỏ hàng sau khi gửi đơn
//        clearCart(tableQr);
//
//        return CheckoutCartDTO.builder()
//                .orderId(createdOrder.getId())  // Lấy từ OrderDTO
//                .diningTableId(createdOrder.getDiningTableId())
//                .status(createdOrder.getStatus())
//                .totalPrice(createdOrder.getTotalPrice())
//                .orderItems(createdOrder.getOrderItems())  // Đúng kiểu List<OrderItemDTO>
//                .build();
//    }
//
//}
//
//
