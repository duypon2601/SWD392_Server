package com.restaurant.rms.mapper;

import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.entity.Food;

public class FoodMapper {
    public static FoodDTO mapToFoodDTO(Food food){
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setFood_id(food.getFood_id());
        foodDTO.setName(food.getName());
        foodDTO.setDescription(food.getDescription());
        foodDTO.setPrice(food.getPrice());
        foodDTO.setImage_url(food.getImage_url());
        foodDTO.setCategory_id(food.getCategory_id());
        foodDTO.setStatus(food.getStatus());

        return foodDTO;
    }
    public static Food mapToFood(FoodDTO foodDTO){
        Food food = new Food();
        food.setFood_id(foodDTO.getFood_id());
        food.setName(foodDTO.getName());
        food.setDescription(foodDTO.getDescription());
        food.setPrice(foodDTO.getPrice());
        food.setImage_url(foodDTO.getImage_url());
        food.setCategory_id(foodDTO.getCategory_id());
        food.setStatus(foodDTO.getStatus());
        return food;
    }
}

