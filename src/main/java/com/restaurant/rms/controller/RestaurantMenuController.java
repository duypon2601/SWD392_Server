package com.restaurant.rms.controller;


import com.restaurant.rms.dto.request.CreateRestaurantMenuDTO;
import com.restaurant.rms.dto.request.CreateRestaurantMenuItemDTO;
import com.restaurant.rms.dto.request.RestaurantMenuDTO;
import com.restaurant.rms.dto.request.UpdateRestaurantMenuDTO;
import com.restaurant.rms.service.restaurantMenuService.RestaurantMenuService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
        if (menuDTO.getMenuItems() == null || menuDTO.getMenuItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Danh sách món ăn không được để trống!");
        }

        for (CreateRestaurantMenuItemDTO item : menuDTO.getMenuItems()) {
            if (item.getFoodId() == null || item.getPrice() == null || item.getQuantity() == null) {
                return ResponseEntity.badRequest().body("Mỗi món ăn phải có foodId, price và quantity!");
            }
        }

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

