//package com.restaurant.rms.controller;
//
//import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
//import com.restaurant.rms.service.RestaurantMenuItemService.RestaurantMenuItemService;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin("*")
//@RestController
//@AllArgsConstructor
//@RequestMapping("/menu_item")
//@SecurityRequirement(name = "api")
//public class RestaurantMenuItemController {
//
//    private final RestaurantMenuItemService restaurantMenuItemService;
//
//    // 🌟 API thêm món ăn vào menu
//    @PostMapping("/{menuId}/items")
//    public ResponseEntity<RestaurantMenuItemDTO> addMenuItem(
//            @PathVariable int menuId,
//            @RequestBody RestaurantMenuItemDTO menuItemDTO) {
//        RestaurantMenuItemDTO createdItem = restaurantMenuItemService.addMenuItemToMenu(menuId, menuItemDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
//    }
//
//    // 📌 API cập nhật món ăn trong menu
//    @PutMapping("/items/{id}")
//    public ResponseEntity<RestaurantMenuItemDTO> updateMenuItem(
//            @PathVariable int id,
//            @RequestBody RestaurantMenuItemDTO menuItemDTO) {
//        RestaurantMenuItemDTO updatedItem = restaurantMenuItemService.updateMenuItem(id, menuItemDTO);
//        return ResponseEntity.ok(updatedItem);
//    }
//
//    // ❌ API xóa món ăn khỏi menu
//    @DeleteMapping("/items/{id}")
//    public ResponseEntity<Void> deleteMenuItem(@PathVariable int id) {
//        restaurantMenuItemService.removeMenuItem(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    // 🔍 API lấy thông tin món ăn theo ID
//    @GetMapping("/items/{id}")
//    public ResponseEntity<RestaurantMenuItemDTO> getMenuItemById(@PathVariable int id) {
//        RestaurantMenuItemDTO menuItemDTO = restaurantMenuItemService.getMenuItemById(id);
//        return ResponseEntity.ok(menuItemDTO);
//    }
//
//    // 📋 API lấy tất cả món ăn của một menu
//    @GetMapping("/{menuId}/items")
//    public ResponseEntity<List<RestaurantMenuItemDTO>> getAllMenuItemsByMenu(@PathVariable int menuId) {
//        List<RestaurantMenuItemDTO> menuItems = restaurantMenuItemService.getAllMenuItemsByMenu(menuId);
//        return ResponseEntity.ok(menuItems);
//    }
//}
//
//
//
