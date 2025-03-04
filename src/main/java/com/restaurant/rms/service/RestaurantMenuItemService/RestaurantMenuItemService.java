package com.restaurant.rms.service.RestaurantMenuItemService;

import com.restaurant.rms.dto.request.RestaurantMenuDTO;
import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;

import java.util.List;

public interface RestaurantMenuItemService {
    RestaurantMenuItemDTO addMenuItemToMenu(int menuId, RestaurantMenuItemDTO menuItemDTO);

    RestaurantMenuItemDTO updateMenuItem(int id, RestaurantMenuItemDTO menuItemDTO);

    void removeMenuItem(int id);

    RestaurantMenuItemDTO getMenuItemById(int id);

    List<RestaurantMenuItemDTO> getAllMenuItemsByMenu(int menuId);
}
