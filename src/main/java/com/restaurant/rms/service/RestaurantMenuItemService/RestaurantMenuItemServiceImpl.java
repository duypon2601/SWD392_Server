package com.restaurant.rms.service.RestaurantMenuItemService;

import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
import com.restaurant.rms.entity.Food;
import com.restaurant.rms.entity.RestaurantMenu;
import com.restaurant.rms.entity.RestaurantMenuItem;
import com.restaurant.rms.mapper.RestaurantMenuItemMapper;
import com.restaurant.rms.repository.FoodRepository;
import com.restaurant.rms.repository.RestaurantMenuItemRepository;
import com.restaurant.rms.repository.RestaurantMenuRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantMenuItemServiceImpl implements RestaurantMenuItemService {

    private final RestaurantMenuItemRepository menuItemRepository;
    private final RestaurantMenuRepository restaurantMenuRepository;
    private final FoodRepository foodRepository;
    private final RestaurantMenuItemMapper menuItemMapper;

    @Override
    public List<RestaurantMenuItemDTO> getMenuItemsByRestaurantMenuId(int restaurantMenuId) {
        List<RestaurantMenuItem> menuItems = menuItemRepository.findByRestaurantMenu_RestaurantMenuId(restaurantMenuId);
        return menuItems.stream().map(menuItemMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RestaurantMenuItemDTO getMenuItemById(int id) {
        RestaurantMenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found with ID: " + id));
        return menuItemMapper.toDTO(menuItem);
    }

    @Override
    @Transactional
    public RestaurantMenuItemDTO createMenuItem(RestaurantMenuItemDTO menuItemDTO) {
        // Tìm menu theo ID
        RestaurantMenu restaurantMenu = restaurantMenuRepository.findById(menuItemDTO.getRestaurantMenuId())
                .orElseThrow(() -> new RuntimeException("Restaurant menu not found!"));

        // Tìm món ăn theo ID
        Food food = foodRepository.findById(menuItemDTO.getFoodId())
                .orElseThrow(() -> new RuntimeException("Food not found!"));

        // Kiểm tra xem món ăn này đã có trong menu chưa
        boolean exists = menuItemRepository.existsByRestaurantMenuAndFood(restaurantMenu, food);
        if (exists) {
            throw new RuntimeException("Món ăn này đã tồn tại trong menu!");
        }

        // Tạo entity mới và đặt giá trị `available = true`
        RestaurantMenuItem menuItem = RestaurantMenuItem.builder()
                .restaurantMenu(restaurantMenu)
                .food(food)
                .price(menuItemDTO.getPrice())
                .isAvailable(true)
                .build();

        // Lưu vào database
        menuItem = menuItemRepository.save(menuItem);

        // Trả về DTO bao gồm `foodName` và `categoryName`
        return RestaurantMenuItemDTO.builder()
                .id(menuItem.getRestaurantMenuItemId())
                .restaurantMenuId(restaurantMenu.getRestaurantMenuId())
                .foodId(food.getFoodId())
                .price(menuItem.getPrice())
                .isAvailable(menuItem.isAvailable())
                .foodName(food.getName())
                .categoryName(food.getCategory().getName())
                .build();
    }


    @Override
    @Transactional
    public RestaurantMenuItemDTO updateMenuItem(int id, RestaurantMenuItemDTO menuItemDTO) {
        RestaurantMenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found!"));

        menuItem.setPrice(menuItemDTO.getPrice());
        menuItem.setAvailable(menuItemDTO.isAvailable());

        menuItem = menuItemRepository.save(menuItem);
        return menuItemMapper.toDTO(menuItem);
    }

    @Override
    @Transactional
    public void deleteMenuItem(int id) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("Menu item not found with ID: " + id);
        }
        menuItemRepository.deleteById(id);
    }
}
