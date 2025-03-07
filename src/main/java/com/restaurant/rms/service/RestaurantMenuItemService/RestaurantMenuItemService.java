package com.restaurant.rms.service.RestaurantMenuItemService;

import com.restaurant.rms.dto.request.CreateRestaurantMenuItemDTO;
import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
import com.restaurant.rms.dto.request.UpdateRestaurantMenuItemDTO;

import java.util.List;

public interface RestaurantMenuItemService {
//    RestaurantMenuItemDTO createRestaurantMenuItem(CreateRestaurantMenuItemDTO menuItemDTO);
    RestaurantMenuItemDTO getRestaurantMenuItemById(Long id);
    List<RestaurantMenuItemDTO> getAllRestaurantMenuItems();
    RestaurantMenuItemDTO updateRestaurantMenuItem(Long id, UpdateRestaurantMenuItemDTO menuItemDTO);
    void deleteRestaurantMenuItem(Long id);
}

