package com.restaurant.rms.mapper;

import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
import com.restaurant.rms.entity.Food;
import com.restaurant.rms.entity.Restaurant;
import com.restaurant.rms.entity.RestaurantMenuItem;

public class RestaurantMenuItemMapper {
    public static RestaurantMenuItemDTO mapToRestaurantMenuItemDTO(RestaurantMenuItem restaurantMenuItem){
        RestaurantMenuItemDTO restaurantMenuItemDTO = new RestaurantMenuItemDTO();
        restaurantMenuItemDTO.setMenu_item_id(restaurantMenuItem.getMenu_item_id());
        restaurantMenuItemDTO.setPrice(restaurantMenuItem.getPrice());
        restaurantMenuItemDTO.setStock_quantity(restaurantMenuItem.getStock_quantity());
        restaurantMenuItemDTO.setMin_stock_threshold(restaurantMenuItem.getMin_stock_threshold());
        // Ánh xạ restaurant_id từ entity Restaurant
        if (restaurantMenuItem.getRestaurant() != null) {
            restaurantMenuItemDTO.setRestaurant_id(restaurantMenuItem.getRestaurant().getRestaurant_id());
        }

        // Ánh xạ food_id từ entity Food
        if (restaurantMenuItem.getFood() != null) {
            restaurantMenuItemDTO.setFood_id(restaurantMenuItem.getFood().getFood_id());
        }

        return restaurantMenuItemDTO;

    }
    public static RestaurantMenuItem mapToRestaurantMenuItem(RestaurantMenuItemDTO restaurantMenuItemDTO){
        RestaurantMenuItem restaurantMenuItem = new RestaurantMenuItem();
        restaurantMenuItem.setMenu_item_id(restaurantMenuItemDTO.getMenu_item_id());
        restaurantMenuItem.setPrice(restaurantMenuItemDTO.getPrice());
        restaurantMenuItem.setStock_quantity(restaurantMenuItemDTO.getStock_quantity());
        restaurantMenuItem.setMin_stock_threshold(restaurantMenuItemDTO.getMin_stock_threshold());

        // Gán Restaurant từ restaurant_id
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurant_id(restaurantMenuItemDTO.getRestaurant_id());
        restaurantMenuItem.setRestaurant(restaurant);

        // Gán Food từ food_id
        Food food = new Food();
        food.setFood_id(restaurantMenuItemDTO.getFood_id());
        restaurantMenuItem.setFood(food);
        return restaurantMenuItem;

    }
}
