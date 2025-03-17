package com.restaurant.rms.service.RestaurantMenuItemService;

import com.restaurant.rms.dto.request.CreateRestaurantMenuItemDTO;
import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;

import java.util.List;

public interface RestaurantMenuItemService {
    List<RestaurantMenuItemDTO> getMenuItemsByRestaurantMenuId(int restaurantMenuId);

    RestaurantMenuItemDTO getMenuItemById(int id);

    RestaurantMenuItemDTO createMenuItem(CreateRestaurantMenuItemDTO menuItemDTO);

    RestaurantMenuItemDTO updateMenuItem(int id, RestaurantMenuItemDTO menuItemDTO);

    void deleteMenuItem(int id);


}


