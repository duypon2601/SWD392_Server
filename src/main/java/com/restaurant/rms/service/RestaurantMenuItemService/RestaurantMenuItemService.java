package com.restaurant.rms.service.RestaurantMenuItemService;

import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
import com.restaurant.rms.util.error.IdInvalidException;

import java.util.List;

public interface RestaurantMenuItemService {
    RestaurantMenuItemDTO createRestaurantMenuItem(RestaurantMenuItemDTO restaurantMenuItemDTO) throws IdInvalidException;

    RestaurantMenuItemDTO getRestaurantMenuItemById ( Integer menu_item_id) throws IdInvalidException;

//    RestaurantMenuItemDTO findRestaurantByUserId(int user_id) throws IdInvalidException;

    List<RestaurantMenuItemDTO> getRestaurantMenuItemAll();

    RestaurantMenuItemDTO updateRestaurantMenuItem (RestaurantMenuItemDTO restaurantDTO, Integer menu_item_id);

    void deleteRestaurantMenuItem (Integer menu_item_id) throws IdInvalidException;
}
