package com.restaurant.rms.controller;

import com.restaurant.rms.dto.request.CreateRestaurantMenuItemDTO;
import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
import com.restaurant.rms.dto.request.UpdateRestaurantMenuItemDTO;
import com.restaurant.rms.service.RestaurantMenuItemService.RestaurantMenuItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/restaurant-menu-items")
@AllArgsConstructor
@SecurityRequirement(name = "api")
public class RestaurantMenuItemController {

    private final RestaurantMenuItemService menuItemService;

    @GetMapping("/menu/{menuId}")
    @PreAuthorize("hasAnyRole('MANAGER') ")
    public ResponseEntity<List<RestaurantMenuItemDTO>> getMenuItemsByMenuId(@PathVariable int menuId) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByRestaurantMenuId(menuId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantMenuItemDTO> getMenuItemById(@PathVariable int id) {
        return ResponseEntity.ok(menuItemService.getMenuItemById(id));
    }

@PostMapping
@PreAuthorize("hasAnyRole('MANAGER') ")
public ResponseEntity<?> createMenuItem(@RequestBody CreateRestaurantMenuItemDTO menuItemDTO) {
    try {
        RestaurantMenuItemDTO createdMenuItem = menuItemService.createMenuItem(menuItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenuItem);
    } catch (RuntimeException e) {
        String errorMessage = e.getMessage();
        if (errorMessage.contains("Restaurant menu not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy menu nhà hàng với ID: " + menuItemDTO.getRestaurantMenuId());
        } else if (errorMessage.contains("Food not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy món ăn với ID: " + menuItemDTO.getFoodId());
        } else if (errorMessage.contains("Món ăn này đã tồn tại trong menu")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Món ăn với ID " + menuItemDTO.getFoodId() + " đã tồn tại trong menu!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi khi tạo món ăn: " + errorMessage);
        }
    }
}


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER') ")
    public ResponseEntity<RestaurantMenuItemDTO> updateMenuItem(@PathVariable int id, @RequestBody UpdateRestaurantMenuItemDTO menuItemDTO) {
        RestaurantMenuItemDTO updatedMenuItem = menuItemService.updateMenuItem(id, menuItemDTO);
        return ResponseEntity.ok(updatedMenuItem);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER') ")
    public ResponseEntity<?> deleteMenuItem(@PathVariable int id) {
        try {
            menuItemService.deleteMenuItem(id);
            return ResponseEntity.ok("Món ăn và các bản ghi liên quan đã được xóa thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không thể xóa món ăn: " + e.getMessage());
        }
    }
}
