package com.restaurant.rms.service.restaurantMenuService;


import com.restaurant.rms.dto.request.CreateRestaurantMenuDTO;
import com.restaurant.rms.dto.request.RestaurantMenuDTO;
import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
import com.restaurant.rms.dto.request.UpdateRestaurantMenuDTO;
import com.restaurant.rms.entity.Food;
import com.restaurant.rms.entity.Restaurant;
import com.restaurant.rms.entity.RestaurantMenu;
import com.restaurant.rms.entity.RestaurantMenuItem;
import com.restaurant.rms.mapper.RestaurantMenuItemMapper;
import com.restaurant.rms.mapper.RestaurantMenuMapper;
import com.restaurant.rms.repository.FoodRepository;
import com.restaurant.rms.repository.RestaurantMenuItemRepository;
import com.restaurant.rms.repository.RestaurantMenuRepository;
import com.restaurant.rms.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantMenuServiceImpl implements RestaurantMenuService {

    private final RestaurantMenuRepository restaurantMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final RestaurantMenuItemRepository menuItemRepository;
    private final RestaurantMenuMapper restaurantMenuMapper; // Thêm mapper vào service

    public RestaurantMenuServiceImpl(RestaurantMenuRepository restaurantMenuRepository,
                                     RestaurantRepository restaurantRepository,
                                     FoodRepository foodRepository,
                                     RestaurantMenuItemRepository menuItemRepository, RestaurantMenuItemMapper restaurantMenuItemMapper,
                                     RestaurantMenuMapper restaurantMenuMapper) { // Inject mapper vào constructor
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.menuItemRepository = menuItemRepository;
        this.restaurantMenuMapper = restaurantMenuMapper;
    }

    @Override
    public RestaurantMenuDTO createRestaurantMenu(CreateRestaurantMenuDTO menuDTO) {
        // Lấy thông tin nhà hàng
        Restaurant restaurant = restaurantRepository.findById(menuDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // Tạo thực đơn mới
        final RestaurantMenu newRestaurantMenu = new RestaurantMenu();
        newRestaurantMenu.setName(menuDTO.getName());
        newRestaurantMenu.setDescription(menuDTO.getDescription());
        newRestaurantMenu.setActive(menuDTO.getIsActive());
        newRestaurantMenu.setRestaurant(restaurant);
        final RestaurantMenu savedMenu = restaurantMenuRepository.save(newRestaurantMenu);

        // Tạo danh sách món ăn trong thực đơn
        List<RestaurantMenuItem> menuItems = menuDTO.getFoodItems().stream().map(foodItem -> {
            Food food = foodRepository.findById(foodItem.getFoodId())
                    .orElseThrow(() -> new RuntimeException("Food not found with id: " + foodItem.getFoodId()));

            return new RestaurantMenuItem(
                    0,
                    foodItem.getPrice(), // Lấy giá từ request
                    foodItem.getQuantity(), // Lấy số lượng từ request
                    10, // Ngưỡng tồn kho tối thiểu
                    true, // Mặc định có sẵn
                    savedMenu,
                    food
            );
        }).collect(Collectors.toList());

        // Lưu danh sách món ăn vào DB
        menuItemRepository.saveAll(menuItems);
        savedMenu.setMenuItems(menuItems);

        return restaurantMenuMapper.toDTO(savedMenu);
    }


    @Override
    public RestaurantMenuDTO getRestaurantMenuById(int id) {
        RestaurantMenu menu = restaurantMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));
        return restaurantMenuMapper.toDTO(menu);
    }


    @Override
    public List<RestaurantMenuDTO> getAllRestaurantMenus() {
        return restaurantMenuRepository.findAll().stream()
                .map(restaurantMenuMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RestaurantMenuDTO updateRestaurantMenu(int id, UpdateRestaurantMenuDTO menuDTO) {
        RestaurantMenu menu = restaurantMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        menu.setName(menuDTO.getName());
        menu.setDescription(menuDTO.getDescription());
        menu.setActive(menuDTO.getIsActive());

        return restaurantMenuMapper.toDTO(restaurantMenuRepository.save(menu));
    }

    @Override
    @Transactional
    public void deleteRestaurantMenu(int id) {
        RestaurantMenu menu = restaurantMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        restaurantMenuRepository.delete(menu);
    }



}


