package com.restaurant.rms.controller;


import com.restaurant.rms.dto.request.CreateRestaurantMenuDTO;
import com.restaurant.rms.dto.request.FoodItemDTO;
import com.restaurant.rms.dto.request.RestaurantMenuDTO;

import com.restaurant.rms.service.restaurantMenuService.RestaurantMenuService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@SecurityRequirement(name = "api")
public class RestaurantMenuController {

    private final RestaurantMenuService restaurantMenuService;

    // 🌟 API tạo thực đơn mới
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER') ")
    public ResponseEntity<?> createRestaurantMenu(@RequestBody CreateRestaurantMenuDTO menuDTO) {
        // Kiểm tra danh sách món ăn không được rỗng
        if (menuDTO.getFoodItems() == null || menuDTO.getFoodItems().isEmpty()) {
            return ResponseEntity.badRequest().body("Danh sách món ăn không được để trống!");
        }

        // Kiểm tra từng món ăn có đủ thông tin không
        for (FoodItemDTO foodItem : menuDTO.getFoodItems()) {
            if (foodItem.getFoodId() == null || foodItem.getPrice() == null) {
                return ResponseEntity.badRequest().body("Mỗi món ăn phải có foodId, price !");
            }
        }

        // Gọi service để tạo thực đơn
        RestaurantMenuDTO createdMenu = restaurantMenuService.createRestaurantMenu(menuDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }


    // ✅ Lấy thông tin thực đơn theo ID
    @GetMapping("/{id}")

    public ResponseEntity<?> getRestaurantMenuById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(restaurantMenuService.getRestaurantMenuById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy thực đơn với ID: " + id);
        }
    }

    // ✅ Lấy thông tin thực đơn theo nhà hàng
    @GetMapping("/restaurant/{restaurantId}")

    public ResponseEntity<?> getMenuByRestaurantId(@PathVariable Integer restaurantId) {
        try {
            List<RestaurantMenuDTO> menus = restaurantMenuService.getMenuByRestaurantId(restaurantId);
            return ResponseEntity.ok(menus);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nhà hàng này chưa có thực đơn!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi lấy thực đơn: " + e.getMessage());
        }
    }

    // ✅ Lấy danh sách tất cả thực đơn
    @GetMapping

    public ResponseEntity<List<RestaurantMenuDTO>> getAllRestaurantMenus() {
        return ResponseEntity.ok(restaurantMenuService.getAllRestaurantMenus());
    }

    // ✅ Xóa thực đơn theo ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER') ")
    public ResponseEntity<?> deleteRestaurantMenu(@PathVariable int id) {
        try {
            restaurantMenuService.deleteRestaurantMenu(id);
            return ResponseEntity.ok("🗑️ Thực đơn đã được xóa thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
//    @PutMapping("/restaurant/{restaurantId}/menu/{menuId}")
//    public ResponseEntity<?> updateMenuByRestaurantId(
//            @PathVariable Integer restaurantId,
//            @PathVariable Integer menuId,
//            @RequestBody RestaurantMenuDTO menuDTO) {
//        try {
//            RestaurantMenuDTO updatedMenu = restaurantMenuService.updateMenuByRestaurantId(restaurantId, menuId, menuDTO);
//            return ResponseEntity.ok(updatedMenu);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    //    // ✅ Cập nhật thực đơn theo ID
//    @PutMapping("/{id}")
//    public ResponseEntity<RestaurantMenuDTO> updateRestaurantMenu(@PathVariable int id, @RequestBody UpdateRestaurantMenuDTO menuDTO) {
//        return ResponseEntity.ok(restaurantMenuService.updateRestaurantMenu(id, menuDTO));
//    }


