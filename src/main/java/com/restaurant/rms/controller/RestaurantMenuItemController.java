package com.restaurant.rms.controller;

import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
import com.restaurant.rms.service.RestaurantMenuItemService.RestaurantMenuItemService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<RestaurantMenuItemDTO>> getMenuItemsByMenuId(@PathVariable int menuId) {
        return ResponseEntity.ok(menuItemService.getMenuItemsByRestaurantMenuId(menuId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantMenuItemDTO> getMenuItemById(@PathVariable int id) {
        return ResponseEntity.ok(menuItemService.getMenuItemById(id));
    }

    @PostMapping
    public ResponseEntity<RestaurantMenuItemDTO> createMenuItem(@RequestBody RestaurantMenuItemDTO menuItemDTO) {
        RestaurantMenuItemDTO createdMenuItem = menuItemService.createMenuItem(menuItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenuItem);
    }


    @PutMapping("/{id}")
    public ResponseEntity<RestaurantMenuItemDTO> updateMenuItem(@PathVariable int id, @RequestBody RestaurantMenuItemDTO menuItemDTO) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, menuItemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable int id) {
        try {
            menuItemService.deleteMenuItem(id);
            return ResponseEntity.ok("Món ăn đã được xóa thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không thể xóa món ăn: " + e.getMessage());
        }
    }
}
