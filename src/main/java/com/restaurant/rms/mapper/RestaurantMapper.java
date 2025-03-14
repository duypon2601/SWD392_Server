package com.restaurant.rms.mapper;


import com.restaurant.rms.dto.request.RestaurantDTO;
import com.restaurant.rms.entity.Restaurant;

public class RestaurantMapper {
    public static RestaurantDTO mapToRestaurantDTO(Restaurant restaurant){
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setRestaurantId(restaurant.getRestaurantId());

        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setLocation(restaurant.getLocation());
        return restaurantDTO;
    }
    public static Restaurant mapToRestaurant(RestaurantDTO restaurantDTO){
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(restaurantDTO.getRestaurantId());

        restaurant.setName(restaurantDTO.getName());
        restaurant.setLocation(restaurantDTO.getLocation());
        return restaurant;
    }
}
