package com.restaurant.rms.mapper;


import com.restaurant.rms.dto.request.CategoryDTO;
import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
import com.restaurant.rms.entity.Category;
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
                .foodId(menuItem.getFood().getFoodId())
                .price(menuItem.getPrice())
                .isAvailable(menuItem.isAvailable())
                .foodName(menuItem.getFood().getName())   // Lấy tên món ăn
                .categoryName(menuItem.getFood().getCategory().getName())
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
                .isAvailable(menuItemDTO.isAvailable())
                .build();
    }
    private CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(category.getCategory_id(), category.getName());
    }
}
