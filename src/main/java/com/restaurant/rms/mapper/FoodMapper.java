package com.restaurant.rms.mapper;

import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.entity.Category;
import com.restaurant.rms.entity.Food;

public class FoodMapper {
    public static FoodDTO mapToFoodDTO(Food food){
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setFoodId(food.getFoodId());
        foodDTO.setName(food.getName());
        foodDTO.setDescription(food.getDescription());
        foodDTO.setImage_url(food.getImage_url());
        if (food.getCategory() != null) {
            foodDTO.setCategory_id(food.getCategory().getCategory_id());
        }

        foodDTO.setStatus(food.getStatus());

        return foodDTO;
    }
    public static Food mapToFood(FoodDTO foodDTO){
        Food food = new Food();
        food.setFoodId(foodDTO.getFoodId());
        food.setName(foodDTO.getName());
        food.setDescription(foodDTO.getDescription());
        food.setImage_url(foodDTO.getImage_url());

        if (foodDTO.getCategory_id() != 0) {
            Category category = new Category();
            category.setCategory_id(foodDTO.getCategory_id());
            food.setCategory(category);
        }

        food.setStatus(foodDTO.getStatus());
        return food;
    }
}

