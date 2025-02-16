package com.restaurant.rms.service.cartService;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.restaurant.rms.entity.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
@RequiredArgsConstructor
public class CartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private String getCartKey(String tableQr) {
        return "cart:" + tableQr;
    }

    // 🛒 Thêm món vào giỏ hàng
    public void addToCart(String tableQr, CartItem item) throws JsonProcessingException {
        String key = getCartKey(tableQr);
        Map<Object, Object> cart = redisTemplate.opsForHash().entries(key);

        String itemKey = String.valueOf(item.getMenu_item_id());
        if (cart.containsKey(itemKey)) {
            CartItem existingItem = objectMapper.readValue((String) cart.get(itemKey), CartItem.class);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            redisTemplate.opsForHash().put(key, itemKey, objectMapper.writeValueAsString(existingItem));
        } else {
            redisTemplate.opsForHash().put(key, itemKey, objectMapper.writeValueAsString(item));
        }
        redisTemplate.expire(key, 30, TimeUnit.MINUTES); // Giữ giỏ hàng trong 30 phút
    }

    // 📜 Lấy danh sách món trong giỏ hàng
    public List<CartItem> getCart(String tableQr) throws JsonProcessingException {
        String key = getCartKey(tableQr);
        Map<Object, Object> cart = redisTemplate.opsForHash().entries(key);
        List<CartItem> items = new ArrayList<>();
        for (Object item : cart.values()) {
            items.add(objectMapper.readValue((String) item, CartItem.class));
        }
        return items;
    }

    // Xóa một món khỏi giỏ hàng
    public void removeFromCart(String tableQr, Long menuItemId) {
        String key = getCartKey(tableQr);
        redisTemplate.opsForHash().delete(key, String.valueOf(menuItemId));
    }

    // 🗑 Xóa toàn bộ giỏ hàng (khi gửi đơn hàng)
    public void clearCart(String tableQr) {
        String key = getCartKey(tableQr);
        redisTemplate.delete(key);
    }


}
