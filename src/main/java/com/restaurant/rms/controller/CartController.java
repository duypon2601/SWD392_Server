package com.restaurant.rms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurant.rms.entity.CartItem;
import com.restaurant.rms.service.cartService.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/cart")
@SecurityRequirement(name = "api")
    public class CartController {

        private final CartService cartService;

        //  Thêm món vào giỏ hàng
        @PostMapping("/{tableQr}/add")
        public ResponseEntity<String> addToCart(@PathVariable String tableQr, @RequestBody CartItem item) throws JsonProcessingException {
            cartService.addToCart(tableQr, item);
            return ResponseEntity.ok("Item added to cart.");
        }

        //  Lấy danh sách món trong giỏ hàng
        @GetMapping("/{tableQr}")
        public ResponseEntity<List<CartItem>> getCart(@PathVariable String tableQr) throws JsonProcessingException {
            return ResponseEntity.ok(cartService.getCart(tableQr));
        }

        //  Xóa một món khỏi giỏ hàng
        @DeleteMapping("/{tableQr}/remove/{menuItemId}")
        public ResponseEntity<String> removeFromCart(@PathVariable String tableQr, @PathVariable Long menuItemId) {
            cartService.removeFromCart(tableQr, menuItemId);
            return ResponseEntity.ok("Item removed.");
        }

        //  Xóa toàn bộ giỏ hàng (khi gửi đơn hàng)
        @DeleteMapping("/{tableQr}/clear")
        public ResponseEntity<String> clearCart(@PathVariable String tableQr) {
            cartService.clearCart(tableQr);
            return ResponseEntity.ok("Cart cleared.");
        }
    }
