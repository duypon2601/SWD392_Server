package com.restaurant.rms.mapper;

import com.restaurant.rms.dto.request.CreateFoodDTO;
import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.entity.Category;
import com.restaurant.rms.entity.Food;
import com.restaurant.rms.enums.Status;

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
        if (food.getCategory() != null) {
            foodDTO.setCategoryName(food.getCategory().getName());
        }
        foodDTO.setStatus(food.getStatus());
        foodDTO.setDeleted(food.isDeleted());

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
        food.setDeleted(foodDTO.isDeleted());
        return food;
    }

    public static Food mapToFood(CreateFoodDTO createFoodDTO) {
        Food food = new Food();
        food.setName(createFoodDTO.getName());
        food.setDescription(createFoodDTO.getDescription());
        food.setImage_url(createFoodDTO.getImage_url());
        if (createFoodDTO.getCategory_id() != 0) {
            Category category = new Category();
            category.setCategory_id(createFoodDTO.getCategory_id());
            food.setCategory(category);
        }
        if (createFoodDTO.getCategory_id() != 0) {
            Category category = new Category();
            category.setName(createFoodDTO.getName());
            food.setCategory(category);
        }
        food.setStatus(Status.AVAILABLE); // Giá trị mặc định cho status
        food.setDeleted(false); // Giá trị mặc định cho isDeleted
        return food;
    }
}

