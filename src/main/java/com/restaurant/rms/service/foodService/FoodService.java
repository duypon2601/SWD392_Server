package com.restaurant.rms.service.foodService;

import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.util.error.IdInvalidException;

import java.util.List;

public interface FoodService {
    FoodDTO createFood(FoodDTO foodDTO) throws IdInvalidException;

    FoodDTO getFoodById(Integer foodId) throws IdInvalidException;

    List<FoodDTO> getAllFood();

    FoodDTO updateFood(FoodDTO updateFood, Integer foodId) throws IdInvalidException;

    void deleteFood(Integer foodId) throws IdInvalidException;

    List<FoodDTO> getFoodsByRestaurant(int restaurantId);
}
