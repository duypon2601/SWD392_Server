package com.restaurant.rms.service.restaurantMenuService;


import com.restaurant.rms.dto.request.CreateRestaurantMenuDTO;
import com.restaurant.rms.dto.request.RestaurantMenuDTO;
import com.restaurant.rms.entity.Food;
import com.restaurant.rms.entity.Restaurant;
import com.restaurant.rms.entity.RestaurantMenu;
import com.restaurant.rms.entity.RestaurantMenuItem;
import com.restaurant.rms.mapper.RestaurantMenuMapper;
import com.restaurant.rms.repository.FoodRepository;
import com.restaurant.rms.repository.RestaurantMenuItemRepository;
import com.restaurant.rms.repository.RestaurantMenuRepository;
import com.restaurant.rms.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
                                     RestaurantMenuItemRepository menuItemRepository,
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
        final RestaurantMenu savedMenu = restaurantMenuRepository.save(newRestaurantMenu); // Lưu vào DB và gán giá trị mới

        // Tạo menuItem từ foodId
        List<RestaurantMenuItem> menuItems = menuDTO.getFoodIds().stream().map(foodId -> {
            Food food = foodRepository.findById(foodId)
                    .orElseThrow(() -> new RuntimeException("Food not found with id: " + foodId));

            return new RestaurantMenuItem(
                    0, // ID tự động tạo
                    new BigDecimal("10.00"), // Giá mặc định
                    100, // Số lượng mặc định
                    10, // Ngưỡng tồn kho tối thiểu
                    true, // Mặc định có sẵn
                    savedMenu, // Sử dụng menu đã lưu
                    food
            );
        }).collect(Collectors.toList());

        menuItemRepository.saveAll(menuItems);
        savedMenu.setMenuItems(menuItems);

        return restaurantMenuMapper.toDTO(savedMenu); // Sử dụng instance của mapper thay vì gọi static
    }



//    @Override
//    public RestaurantMenuDTO updateRestaurantMenu(int id, RestaurantMenuDTO restaurantMenuDTO) {
//        Optional<RestaurantMenu> optionalMenu = restaurantMenuRepository.findById(id);
//        if (optionalMenu.isPresent()) {
//            RestaurantMenu restaurantMenu = optionalMenu.get();
//            restaurantMenu.setName(restaurantMenuDTO.getName());
//            restaurantMenu.setDescription(restaurantMenuDTO.getDescription());
//            restaurantMenu.setActive(restaurantMenuDTO.isActive());
//            restaurantMenu = restaurantMenuRepository.save(restaurantMenu);
//            return restaurantMenuMapper.toDTO(restaurantMenu);
//        }
//        throw new EntityNotFoundException("Menu not found");
//    }
//
//    @Override
//    public void deleteRestaurantMenu(int id) {
//        restaurantMenuRepository.deleteById(id);
//    }
//
//    @Override
//    public RestaurantMenuDTO getRestaurantMenuById(int id) {
//        return restaurantMenuRepository.findById(id)
//                .map(restaurantMenuMapper::toDTO)
//                .orElseThrow(() -> new EntityNotFoundException("Menu not found"));
//    }
//
//    @Override
//    public List<RestaurantMenuDTO> getAllRestaurantMenus() {
//        return restaurantMenuRepository.findAll().stream()
//                .map(restaurantMenuMapper::toDTO)
//                .collect(Collectors.toList());
//    }
}


