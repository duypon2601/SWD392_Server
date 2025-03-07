package com.restaurant.rms.service.restaurantMenuService;

import com.restaurant.rms.dto.request.CreateRestaurantMenuDTO;
import com.restaurant.rms.dto.request.RestaurantMenuDTO;
import com.restaurant.rms.dto.request.UpdateRestaurantMenuDTO;

import java.util.List;

public interface RestaurantMenuService {
    RestaurantMenuDTO createRestaurantMenu(CreateRestaurantMenuDTO menuDTO);
    RestaurantMenuDTO getRestaurantMenuById(int id);
    List<RestaurantMenuDTO> getAllRestaurantMenus();
    RestaurantMenuDTO updateRestaurantMenu(int id, UpdateRestaurantMenuDTO menuDTO);
    void deleteRestaurantMenu(int id);
}

