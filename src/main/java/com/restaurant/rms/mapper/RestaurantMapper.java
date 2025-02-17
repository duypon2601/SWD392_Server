package com.restaurant.rms.mapper;


import com.restaurant.rms.dto.request.RestaurantDTO;
import com.restaurant.rms.entity.Restaurant;

public class RestaurantMapper {
    public static RestaurantDTO mapToRestaurantDTO(Restaurant restaurant){
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setRestaurant_id(restaurant.getRestaurant_id());
        restaurantDTO.setManager_id(restaurant.getManager_id());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setLocation(restaurant.getLocation());
        return restaurantDTO;
    }
    public static Restaurant mapToRestaurant(RestaurantDTO restaurantDTO){
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurant_id(restaurantDTO.getRestaurant_id());
        restaurant.setManager_id(restaurantDTO.getManager_id());
        restaurant.setName(restaurantDTO.getName());
        restaurant.setLocation(restaurantDTO.getLocation());
        return restaurant;
    }
}
