package com.restaurant.rms.mapper;


import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
import com.restaurant.rms.entity.Food;
import com.restaurant.rms.entity.RestaurantMenu;
import com.restaurant.rms.entity.RestaurantMenuItem;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMenuItemMapper {

    public RestaurantMenuItemDTO toDTO(RestaurantMenuItem menuItem) {
        if (menuItem == null) {
            return null;
        }
        return RestaurantMenuItemDTO.builder()
                .id(menuItem.getRestaurantMenuItemId())
                .restaurantMenuId(menuItem.getRestaurantMenu().getRestaurantMenuId())
                .foodId(menuItem.getFood().getFood_id())
                .price(menuItem.getPrice())
                .stockQuantity(menuItem.getStockQuantity())
                .minStockThreshold(menuItem.getMinStockThreshold())
                .isAvailable(menuItem.isAvailable())
                .build();
    }

    public RestaurantMenuItem toEntity(RestaurantMenuItemDTO menuItemDTO, RestaurantMenu restaurantMenu, Food food) {
        if (menuItemDTO == null || restaurantMenu == null || food == null) {
            return null;
        }
        return RestaurantMenuItem.builder()
                .restaurantMenuItemId(menuItemDTO.getId()) // ID nếu cập nhật, khi tạo mới ID sẽ được tự động tạo
                .restaurantMenu(restaurantMenu)
                .food(food)
                .price(menuItemDTO.getPrice())
                .stockQuantity(menuItemDTO.getStockQuantity())
                .minStockThreshold(menuItemDTO.getMinStockThreshold())
                .isAvailable(menuItemDTO.isAvailable())
                .build();
    }
}
