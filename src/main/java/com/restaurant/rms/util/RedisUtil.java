package com.restaurant.rms.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.restaurant.rms.dto.request.CartItemDTO;
import com.restaurant.rms.dto.request.UpdateCartItemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Lấy giỏ hàng từ Redis theo key
     */
    public List<CartItemDTO> getCart(String key) {
        try {
            List<String> cartJson = redisTemplate.opsForList().range(key, 0, -1);
            if (cartJson == null || cartJson.isEmpty()) {
                return List.of();
            }
            return cartJson.stream()
                    .map(json -> {
                        try {
                            return objectMapper.readValue(json, CartItemDTO.class);
                        } catch (JsonProcessingException e) {
                            log.error("❌ Lỗi khi parse JSON giỏ hàng", e);
                            return null;
                        }
                    })
                    .filter(cartItem -> cartItem != null)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("❌ Lỗi khi lấy giỏ hàng từ Redis", e);
            return List.of();
        }
    }

    /**
     * Thêm một món vào giỏ hàng
     */
    public void addToCart(String key, CartItemDTO cartItem) {
        try {
            String json = objectMapper.writeValueAsString(cartItem);
            redisTemplate.opsForList().rightPush(key, json);
            redisTemplate.expire(key, 1, TimeUnit.HOURS); // Set TTL cho giỏ hàng (1 giờ)
        } catch (JsonProcessingException e) {
            log.error("❌ Lỗi khi chuyển đổi CartItemDTO sang JSON", e);
        }
    }

    /**
     * Cập nhật toàn bộ giỏ hàng (ghi đè danh sách)
     */
    public void updateCart(String key, List<CartItemDTO> cartItems) {
        try {
            redisTemplate.delete(key); // Xóa giỏ cũ
            for (CartItemDTO item : cartItems) {
                addToCart(key, item);
            }
        } catch (Exception e) {
            log.error("❌ Lỗi khi cập nhật giỏ hàng", e);
        }
    }

    /**
     * Cập nhật một CartItem cụ thể trong giỏ hàng
     */
    public void updateCartItem(String key, UpdateCartItemDTO updatedItem) {
        try {
            // Lấy giỏ hàng hiện tại
            List<CartItemDTO> currentCart = getCart(key);
            if (currentCart.isEmpty()) {
                log.warn("Giỏ hàng trống, không thể cập nhật item với menuItemId: {}", updatedItem.getMenuItemId());
                return;
            }

            // Tìm và cập nhật CartItem
            boolean itemFound = false;
            for (int i = 0; i < currentCart.size(); i++) {
                CartItemDTO item = currentCart.get(i);
                if (item.getMenuItemId() == updatedItem.getMenuItemId()) {
                    // Giữ nguyên price, chỉ cập nhật quantity
                    CartItemDTO updatedCartItem = new CartItemDTO();
                    updatedCartItem.setMenuItemId(item.getMenuItemId());
                    updatedCartItem.setQuantity(updatedItem.getQuantity()); // Cập nhật quantity từ UpdateCartItemDTO

                    currentCart.set(i, updatedCartItem);
                    itemFound = true;
                    break;
                }
            }

            if (!itemFound) {
                log.warn("Không tìm thấy item với menuItemId: {} để cập nhật", updatedItem.getMenuItemId());
                return;
            }

            // Cập nhật lại danh sách trên Redis
            redisTemplate.delete(key); // Xóa danh sách cũ
            for (CartItemDTO item : currentCart) {
                addToCart(key, item); // Thêm lại từng item
            }
            redisTemplate.expire(key, 1, TimeUnit.HOURS); // Đặt lại TTL
            log.info("Đã cập nhật CartItem với menuItemId: {}", updatedItem.getMenuItemId());
        } catch (Exception e) {
            log.error("❌ Lỗi khi cập nhật CartItem trong giỏ hàng", e);
        }
    }

    /**
     * Xóa một món trong giỏ hàng theo menuItemId
     */
    public void removeItemFromCart(String key, int menuItemId) {
        List<CartItemDTO> currentCart = getCart(key);
        List<CartItemDTO> updatedCart = currentCart.stream()
                .filter(item -> item.getMenuItemId() != menuItemId)
                .collect(Collectors.toList());
        updateCart(key, updatedCart);
    }

    /**
     * Xóa toàn bộ giỏ hàng
     */
    public void clearCart(String key) {
        redisTemplate.delete(key);
        log.info("🗑 Đã xóa giỏ hàng với key: {}", key);
    }
}

