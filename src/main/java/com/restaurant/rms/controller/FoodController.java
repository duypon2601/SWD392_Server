package com.restaurant.rms.controller;

import com.restaurant.rms.service.foodService.FoodService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("api")
@SecurityRequirement(name = "api")
public class FoodController {

    @Autowired
    FoodService foodService;

//    @PostMapping("/foodcreate")
//    public ResponseEntity<Food> createFood(@RequestBody FoodDTO foodDTO) {
//        Food food = foodService.createFood();
//    }
}
