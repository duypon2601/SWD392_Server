package com.restaurant.rms.mapper;

import com.restaurant.rms.dto.request.RestaurantMenuDTO;
import com.restaurant.rms.entity.Food;
import com.restaurant.rms.entity.Restaurant;
import com.restaurant.rms.entity.RestaurantMenu;
import com.restaurant.rms.entity.RestaurantMenuItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestaurantMenuMapper {

    private final RestaurantMenuItemMapper menuItemMapper; // ✅ Không sử dụng static nữa

    public RestaurantMenuDTO toDTO(RestaurantMenu restaurantMenu) { // ✅ Bỏ static
        if (restaurantMenu == null) {
            return null;
        }
        return RestaurantMenuDTO.builder()
                .id(restaurantMenu.getRestaurantMenuId())
                .restaurantId(restaurantMenu.getRestaurant().getRestaurantId())
                .name(restaurantMenu.getName())
                .description(restaurantMenu.getDescription())
                .isActive(restaurantMenu.isActive())
                .menuItems(restaurantMenu.getMenuItems() != null
                        ? restaurantMenu.getMenuItems().stream()
                        .map(menuItemMapper::toDTO) // ✅ Không còn lỗi static
                        .collect(Collectors.toList())
                        : new ArrayList<>()) // ✅ Tránh lỗi null
                .build();
    }

    public RestaurantMenu toEntity(RestaurantMenuDTO menuDTO, Restaurant restaurant, List<Food> availableFoods) {
        if (menuDTO == null) {
            return null;
        }
        RestaurantMenu restaurantMenu = new RestaurantMenu();
        restaurantMenu.setRestaurantMenuId(menuDTO.getId());
        restaurantMenu.setName(menuDTO.getName());
        restaurantMenu.setDescription(menuDTO.getDescription());
        restaurantMenu.setActive(menuDTO.isActive());
        restaurantMenu.setRestaurant(restaurant);

        List<RestaurantMenuItem> menuItems = new ArrayList<>();
        if (menuDTO.getMenuItems() != null) {
            menuItems = menuDTO.getMenuItems().stream()
                    .map(itemDTO -> {
                        Food food = availableFoods.stream()
                                .filter(f -> f.getFood_id() == itemDTO.getFoodId())
                                .findFirst()
                                .orElse(null);
                        return food != null ? menuItemMapper.toEntity(itemDTO, restaurantMenu, food) : null;
                    })
                    .filter(Objects::nonNull) // Loại bỏ các item null nếu không tìm thấy food
                    .collect(Collectors.toList());
        }
        restaurantMenu.setMenuItems(menuItems);
        return restaurantMenu;
    }

}

