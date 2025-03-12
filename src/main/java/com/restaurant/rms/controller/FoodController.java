package com.restaurant.rms.controller;

import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.service.foodService.FoodService;
import com.restaurant.rms.util.error.IdInvalidException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/food")
@SecurityRequirement(name = "api")
public class FoodController {

    private final FoodService foodService;

    @PostMapping
    public ResponseEntity<FoodDTO> createFood(@RequestBody FoodDTO foodDTO) throws IdInvalidException {
        return ResponseEntity.ok(foodService.createFood(foodDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodDTO> getFoodById(@PathVariable("id") Integer foodId) throws IdInvalidException {
        return ResponseEntity.ok(foodService.getFoodById(foodId));
    }

    @GetMapping
    public ResponseEntity<List<FoodDTO>> getAllFood() {
        return ResponseEntity.ok(foodService.getAllFood());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodDTO> updateFood(@RequestBody FoodDTO foodDTO, @PathVariable("id") Integer foodId) throws IdInvalidException {
        return ResponseEntity.ok(foodService.updateFood(foodDTO, foodId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable("id") Integer foodId) {
        try {
            foodService.deleteFood(foodId);
            return ResponseEntity.ok("Đã xóa Food ID " + foodId);
        } catch (IdInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(" Lỗi hệ thống: " + e.getMessage());
        }
    }

//    @GetMapping("/menu/{restaurantId}")
//    public ResponseEntity<List<FoodDTO>> getFoodsByRestaurant(@PathVariable int restaurantId) {
//        List<FoodDTO> foods = foodService.getFoodsByRestaurant(restaurantId);
//        return ResponseEntity.ok(foods);
//    }
}
