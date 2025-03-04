//package com.restaurant.rms.controller;
//
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.restaurant.rms.dto.request.CartItemDTO;
//import com.restaurant.rms.dto.request.CheckoutCartDTO;
//import com.restaurant.rms.service.cartService.CartService;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@CrossOrigin("*")
//@RestController
//@AllArgsConstructor
//@RequestMapping("/cart")
//@SecurityRequirement(name = "api")
//public class CartController {
//
//    private final CartService cartService;
//
//    @GetMapping("/{tableQr}")
//    public ResponseEntity<List<CartItemDTO>> getCart(@PathVariable String tableQr) {
//        log.info("📦 Lấy giỏ hàng của bàn {}", tableQr);
//        return ResponseEntity.ok(cartService.getCart(tableQr));
//    }
//
//    @PostMapping("/{tableQr}/add")
//    public ResponseEntity<String> addToCart(@PathVariable String tableQr, @RequestBody CartItemDTO cartItem) {
//        cartService.addToCart(tableQr, cartItem);
//        return ResponseEntity.ok("Thêm món thành công!");
//    }
//
//    @PutMapping("/{tableQr}/update")
//    public ResponseEntity<String> updateCart(@PathVariable String tableQr, @RequestBody List<CartItemDTO> updatedCart) {
//        cartService.updateCart(tableQr, updatedCart);
//        return ResponseEntity.ok("Cập nhật giỏ hàng thành công!");
//    }
//
//    @DeleteMapping("/{tableQr}/remove/{menuItemId}")
//    public ResponseEntity<String> removeItem(@PathVariable String tableQr, @PathVariable int menuItemId) {
//        cartService.removeItemFromCart(tableQr, menuItemId);
//        return ResponseEntity.ok("Xóa món thành công!");
//    }
//
//    @DeleteMapping("/{tableQr}/clear")
//    public ResponseEntity<String> clearCart(@PathVariable String tableQr) {
//        cartService.clearCart(tableQr);
//        return ResponseEntity.ok("Đã xóa giỏ hàng!");
//    }
//
//    @PostMapping("/{tableQr}/checkout")
//    public ResponseEntity<CheckoutCartDTO> checkout(@PathVariable String tableQr) throws JsonProcessingException {
//        log.info("🚀 Checkout giỏ hàng của bàn {}", tableQr);
//        return ResponseEntity.ok(cartService.checkoutAndCreateOrder(tableQr));
//    }
//}
//
