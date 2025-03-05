package com.restaurant.rms.service.restaurantMenuService;

import com.restaurant.rms.dto.request.CreateRestaurantMenuDTO;
import com.restaurant.rms.dto.request.RestaurantMenuDTO;

import java.util.List;

public interface RestaurantMenuService {
    RestaurantMenuDTO createRestaurantMenu(CreateRestaurantMenuDTO menuDTO);
//    RestaurantMenuDTO updateRestaurantMenu(int id, RestaurantMenuDTO restaurantMenuDTO);
//    void deleteRestaurantMenu(int id);
//    RestaurantMenuDTO getRestaurantMenuById(int id);
//    List<RestaurantMenuDTO> getAllRestaurantMenus();
}

