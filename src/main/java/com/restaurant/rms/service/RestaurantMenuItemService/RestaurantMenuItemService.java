package com.restaurant.rms.service.RestaurantMenuItemService;

import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;

import java.util.List;

public interface RestaurantMenuItemService {
    List<RestaurantMenuItemDTO> getMenuItemsByRestaurantMenuId(int restaurantMenuId);

    RestaurantMenuItemDTO getMenuItemById(int id);

    RestaurantMenuItemDTO createMenuItem(RestaurantMenuItemDTO menuItemDTO);

    RestaurantMenuItemDTO updateMenuItem(int id, RestaurantMenuItemDTO menuItemDTO);

    void deleteMenuItem(int id);
}


