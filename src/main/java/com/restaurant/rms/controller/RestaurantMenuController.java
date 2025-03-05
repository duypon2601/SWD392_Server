package com.restaurant.rms.controller;


import com.restaurant.rms.dto.request.CreateRestaurantMenuDTO;
import com.restaurant.rms.dto.request.RestaurantMenuDTO;
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
    public ResponseEntity<RestaurantMenuDTO> createRestaurantMenu(@RequestBody CreateRestaurantMenuDTO menuDTO) {
        RestaurantMenuDTO createdMenu = restaurantMenuService.createRestaurantMenu(menuDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }

//    // 📌 API cập nhật thực đơn
//    @PutMapping("/{id}")
//    public ResponseEntity<RestaurantMenuDTO> updateMenu(
//            @PathVariable int id,
//            @RequestBody RestaurantMenuDTO menuDTO) {
//        RestaurantMenuDTO updatedMenu = restaurantMenuService.updateRestaurantMenu(id, menuDTO);
//        return ResponseEntity.ok(updatedMenu);
//    }
//
//    // ❌ API xóa thực đơn
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteMenu(@PathVariable int id) {
//        restaurantMenuService.deleteRestaurantMenu(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    // 🔍 API lấy thực đơn theo ID
//    @GetMapping("/{id}")
//    public ResponseEntity<RestaurantMenuDTO> getMenuById(@PathVariable int id) {
//        RestaurantMenuDTO menuDTO = restaurantMenuService.getRestaurantMenuById(id);
//        return ResponseEntity.ok(menuDTO);
//    }
//
//    // 📋 API lấy tất cả thực đơn
//    @GetMapping
//    public ResponseEntity<List<RestaurantMenuDTO>> getAllMenus() {
//        List<RestaurantMenuDTO> menus = restaurantMenuService.getAllRestaurantMenus();
//        return ResponseEntity.ok(menus);
//    }
}

