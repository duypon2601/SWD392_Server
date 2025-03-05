package com.restaurant.rms.service.cartService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurant.rms.dto.request.CartItemDTO;
import com.restaurant.rms.dto.request.CheckoutCartDTO;
import com.restaurant.rms.dto.request.orderDTO.OrderDTO;
import com.restaurant.rms.dto.request.orderDTO.OrderItemDTO;
import com.restaurant.rms.entity.DiningTable;
import com.restaurant.rms.entity.RestaurantMenuItem;
import com.restaurant.rms.enums.OrderStatus;
import com.restaurant.rms.mapper.CartItemMapper;
import com.restaurant.rms.repository.DiningTableRepository;
import com.restaurant.rms.repository.RestaurantMenuItemRepository;
import com.restaurant.rms.repository.RestaurantRepository;
import com.restaurant.rms.service.orderService.OrderService;
import com.restaurant.rms.util.RedisUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final RedisUtil redisUtil;
    private final RestaurantMenuItemRepository menuItemRepository;
    private final DiningTableRepository diningTableRepository;
    private final OrderService orderService;
    private String getCartKey(String tableQr) {
        return "cart:" + tableQr;
    }

    public List<CartItemDTO> getCart(String tableQr) {
        return redisUtil.getCart(getCartKey(tableQr));
    }

    public void addToCart(String tableQr, int menuItemId, int quantity) {
        RestaurantMenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("🚨 Không tìm thấy món ăn với menuItemId: " + menuItemId));

        CartItemDTO cartItem = CartItemMapper.toDTO(menuItem, quantity);
        redisUtil.addToCart(getCartKey(tableQr), cartItem);

        log.info("➕ Đã thêm món vào giỏ hàng: {}", cartItem);
    }

    public void updateCart(String tableQr, List<CartItemDTO> updatedCart) {
        log.info("🔄 Cập nhật giỏ hàng: {}", updatedCart);
        redisUtil.updateCart(getCartKey(tableQr), updatedCart);
    }

    public void removeItemFromCart(String tableQr, int menuItemId) {
        log.info("❌ Xóa món {} khỏi giỏ hàng", menuItemId);
        redisUtil.removeItemFromCart(getCartKey(tableQr), menuItemId);
    }

    public void clearCart(String tableQr) {
        log.info("🗑 Xóa toàn bộ giỏ hàng của bàn {}", tableQr);
        redisUtil.clearCart(getCartKey(tableQr));
    }


    @Transactional
    public CheckoutCartDTO checkoutAndCreateOrder(String tableQr) throws JsonProcessingException {
        log.info("🛒 Bắt đầu checkout cho bàn: {}", tableQr);

        String key = getCartKey(tableQr);
        List<CartItemDTO> cartItems = getCart(tableQr);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("🚨 Giỏ hàng trống!");
        }

        DiningTable table = diningTableRepository.findByQrCode(tableQr)
                .orElseThrow(() -> new RuntimeException("Bàn không tồn tại!"));

        List<OrderItemDTO> orderItemDTOs = cartItems.stream()
                .map(cartItem -> OrderItemDTO.builder()
                        .menuItemId(cartItem.getMenuItemId())
                        .quantity(cartItem.getQuantity())
                        .price(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                        .build())
                .collect(Collectors.toList());

        BigDecimal totalPrice = orderItemDTOs.stream()
                .map(OrderItemDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("🚨 Tổng tiền không hợp lệ!");
        }

        OrderDTO orderDTO = OrderDTO.builder()
                .diningTableId(table.getDiningTableId())
                .status(OrderStatus.PENDING.name())
                .totalPrice(totalPrice)
                .orderItems(orderItemDTOs)
                .build();

        OrderDTO createdOrder = orderService.createOrderOrSubOrder(orderDTO);
        clearCart(tableQr);

        return CheckoutCartDTO.builder()
                .orderId(createdOrder.getId())
                .diningTableId(createdOrder.getDiningTableId())
                .status(createdOrder.getStatus())
                .totalPrice(createdOrder.getTotalPrice())
                .orderItems(createdOrder.getOrderItems())
                .build();
    }


}


