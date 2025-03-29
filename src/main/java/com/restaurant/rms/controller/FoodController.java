package com.restaurant.rms.controller;

import com.restaurant.rms.dto.request.CreateFoodDTO;
import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.service.foodService.FoodService;
import com.restaurant.rms.util.error.IdInvalidException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN') ")
    public ResponseEntity<FoodDTO> createFood(@RequestBody CreateFoodDTO createFoodDTO) throws IdInvalidException {
        return ResponseEntity.ok(foodService.createFood(createFoodDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') ")
    public ResponseEntity<FoodDTO> getFoodById(@PathVariable("id") Integer foodId) throws IdInvalidException {
        return ResponseEntity.ok(foodService.getFoodById(foodId));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') ")
    public ResponseEntity<List<FoodDTO>> getAllFood() {
        return ResponseEntity.ok(foodService.getAllFood());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN') ")
    public ResponseEntity<FoodDTO> updateFood(@RequestBody FoodDTO foodDTO, @PathVariable("id") Integer foodId) throws IdInvalidException {
        return ResponseEntity.ok(foodService.updateFood(foodDTO, foodId));
    }
//    xóa vĩnh viễn food
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteFood(@PathVariable("id") Integer foodId) {
//        try {
//            foodService.deleteFood(foodId);
//            return ResponseEntity.ok("Đã xóa Food ID " + foodId);
//        } catch (IdInvalidException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body( e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(" Lỗi hệ thống: " + e.getMessage());
//        }
//    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN') ")
    public ResponseEntity<String> deleteFood(@PathVariable("id") Integer foodId) {
        try {
            foodService.deleteFood(foodId);
            return ResponseEntity.ok("Đã đánh dấu xóa mềm Food ID " + foodId);
        } catch (IdInvalidException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @GetMapping("/deleted")
    @PreAuthorize("hasAnyRole('ADMIN') ")
    public ResponseEntity<List<FoodDTO>> getAllDeletedFood() {
        return ResponseEntity.ok(foodService.getAllDeletedFood());
    }

    @PutMapping("/{id}/restore")
    @PreAuthorize("hasAnyRole('ADMIN') ")
    public ResponseEntity<FoodDTO> restoreFood(@PathVariable("id") Integer foodId) throws IdInvalidException {
        try {
            FoodDTO restoredFood = foodService.restoreFood(foodId);
            return ResponseEntity.ok(restoredFood);
        } catch (IdInvalidException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi hệ thống: " + e.getMessage());
        }
    }


}
