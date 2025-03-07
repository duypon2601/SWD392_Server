package com.restaurant.rms.controller;


import com.restaurant.rms.dto.request.CreateRestaurantMenuDTO;
import com.restaurant.rms.dto.request.FoodItemDTO;
import com.restaurant.rms.dto.request.RestaurantMenuDTO;
import com.restaurant.rms.dto.request.UpdateRestaurantMenuDTO;
import com.restaurant.rms.service.restaurantMenuService.RestaurantMenuService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@SecurityRequirement(name = "api")
public class RestaurantMenuController {

    private final RestaurantMenuService restaurantMenuService;

    // 🌟 API tạo thực đơn mới
    @PostMapping
    public ResponseEntity<?> createRestaurantMenu(@RequestBody CreateRestaurantMenuDTO menuDTO) {
        // Kiểm tra danh sách món ăn không được rỗng
        if (menuDTO.getFoodItems() == null || menuDTO.getFoodItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Danh sách món ăn không được để trống!");
        }

        // Kiểm tra từng món ăn có đủ thông tin không
        for (FoodItemDTO foodItem : menuDTO.getFoodItems()) {
            if (foodItem.getFoodId() == null || foodItem.getPrice() == null || foodItem.getQuantity() == null) {
                return ResponseEntity.badRequest().body("Mỗi món ăn phải có foodId, price và quantity!");
            }
        }

        // Gọi service để tạo thực đơn
        RestaurantMenuDTO createdMenu = restaurantMenuService.createRestaurantMenu(menuDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }



    // ✅ Lấy thông tin thực đơn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantMenuDTO> getRestaurantMenuById(@PathVariable int id) {
        return ResponseEntity.ok(restaurantMenuService.getRestaurantMenuById(id));
    }

    // ✅ Lấy danh sách tất cả thực đơn
    @GetMapping
    public ResponseEntity<List<RestaurantMenuDTO>> getAllRestaurantMenus() {
        return ResponseEntity.ok(restaurantMenuService.getAllRestaurantMenus());
    }

    // ✅ Cập nhật thực đơn theo ID
    @PutMapping("/{id}")
    public ResponseEntity<RestaurantMenuDTO> updateRestaurantMenu(@PathVariable int id, @RequestBody UpdateRestaurantMenuDTO menuDTO) {
        return ResponseEntity.ok(restaurantMenuService.updateRestaurantMenu(id, menuDTO));
    }

    // ✅ Xóa thực đơn theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurantMenu(@PathVariable int id) {
        restaurantMenuService.deleteRestaurantMenu(id);
        return ResponseEntity.noContent().build();
    }
}

