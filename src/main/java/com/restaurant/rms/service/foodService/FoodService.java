package com.restaurant.rms.service.foodService;

import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.util.error.IdInvalidException;

import java.util.List;

public interface FoodService {
    FoodDTO createFood(FoodDTO foodDTO) throws IdInvalidException;

    FoodDTO getFoodById ( Integer food_id) throws IdInvalidException;

    List<FoodDTO> getFoodAll();

    FoodDTO updateFood(FoodDTO updateFood, Integer food_id);

    void deleteFood (Integer food_id) throws IdInvalidException;

    FoodDTO handleGetFoodByName(String name);

    boolean isFoodNameExist (String name);
}
