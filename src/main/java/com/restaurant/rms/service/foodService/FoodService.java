package com.restaurant.rms.service.foodService;

import com.restaurant.rms.dto.request.CreateFoodDTO;
import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.util.error.IdInvalidException;

import java.util.List;

public interface FoodService {
    FoodDTO createFood(CreateFoodDTO createFoodDTO) throws IdInvalidException;

    FoodDTO getFoodById(Integer foodId) throws IdInvalidException;

    List<FoodDTO> getAllFood();

    FoodDTO updateFood(FoodDTO updateFood, Integer foodId) throws IdInvalidException;

    void deleteFood(Integer foodId) throws IdInvalidException;

    List<FoodDTO> getAllDeletedFood(); // Thêm phương thức lấy Food đã xóa mềm
    FoodDTO restoreFood(Integer foodId) throws IdInvalidException;

//    List<FoodDTO> getFoodsByRestaurant(int restaurantId);
}
