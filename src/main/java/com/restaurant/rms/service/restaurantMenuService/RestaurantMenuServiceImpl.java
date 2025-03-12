package com.restaurant.rms.service.restaurantMenuService;


import com.restaurant.rms.dto.request.CreateRestaurantMenuDTO;
import com.restaurant.rms.dto.request.RestaurantMenuDTO;
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
//    private final NotificationController notificationController;
    private final RestaurantMenuItemMapper restaurantMenuItemMapper;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;

    public RestaurantMenuServiceImpl(RestaurantMenuRepository restaurantMenuRepository,
                                     RestaurantRepository restaurantRepository,
                                     FoodRepository foodRepository,
                                     RestaurantMenuItemRepository menuItemRepository, RestaurantMenuItemMapper restaurantMenuItemMapper,
                                     RestaurantMenuMapper restaurantMenuMapper, RestaurantMenuItemMapper restaurantMenuItemMapper1, RestaurantMenuItemRepository restaurantMenuItemRepository) { // Inject mapper vào constructor
        this.restaurantMenuRepository = restaurantMenuRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.menuItemRepository = menuItemRepository;
        this.restaurantMenuMapper = restaurantMenuMapper;
//        this.notificationController = notificationController;
        this.restaurantMenuItemMapper = restaurantMenuItemMapper1;
        this.restaurantMenuItemRepository = restaurantMenuItemRepository;
    }

    @Override
    public RestaurantMenuDTO createRestaurantMenu(CreateRestaurantMenuDTO menuDTO) {
        // Lấy thông tin nhà hàng
        Restaurant restaurant = restaurantRepository.findById(menuDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        // Kiểm tra xem nhà hàng đã có menu chưa
        boolean menuExists = restaurantMenuRepository.existsByRestaurant(restaurant);
        if (menuExists) {
            throw new RuntimeException("Nhà hàng này đã có menu, không thể tạo thêm!");
        }
        // Tạo thực đơn mới
        final RestaurantMenu newRestaurantMenu = new RestaurantMenu();
//        newRestaurantMenu.setName(menuDTO.getName());
//        newRestaurantMenu.setDescription(menuDTO.getDescription());
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
//                    foodItem.getQuantity(), // Lấy số lượng từ request
//                    10, // Ngưỡng tồn kho tối thiểu
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
    public List<RestaurantMenuDTO> getMenuByRestaurantId(Integer restaurantId) {
        List<RestaurantMenu> menus = restaurantMenuRepository.findByRestaurant_RestaurantId(restaurantId);

        if (menus.isEmpty()) {
            throw new RuntimeException(" Không tìm thấy menu nào cho nhà hàng ID: " + restaurantId);
        }

        return menus.stream().map(menu -> RestaurantMenuDTO.builder()
                .id(menu.getRestaurantMenuId())
//                .name(menu.getName())
//                .description(menu.getDescription())
                .isActive(menu.isActive())
                .menuItems(menu.getMenuItems().stream()
                        .map(restaurantMenuItemMapper::toDTO)
                        .collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }

//    @Override
//    @Transactional
//    public RestaurantMenuDTO updateRestaurantMenu(int id, UpdateRestaurantMenuDTO menuDTO) {
//        RestaurantMenu menu = restaurantMenuRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Menu not found"));
//
////        menu.setName(menuDTO.getName());
////        menu.setDescription(menuDTO.getDescription());
//        menu.setActive(menuDTO.getIsActive());
//
//        // Gửi thông báo sau khi lưu thành công
//        String message = " Menu của nhà hàng ID " + id + " đã được cập nhật!";
//        notificationController.sendMenuUpdate(message);
//        System.out.println(" Đã gửi thông báo: " + message);
//        return restaurantMenuMapper.toDTO(restaurantMenuRepository.save(menu));
//    }

    @Override
    @Transactional
    public void deleteRestaurantMenu(int id) {
        RestaurantMenu menu = restaurantMenuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        restaurantMenuRepository.delete(menu);
    }
//    @Override
//    @Transactional
//    public RestaurantMenuDTO updateMenuByRestaurantId(Integer restaurantId, Integer menuId, RestaurantMenuDTO menuDTO) {
//        RestaurantMenu menu = restaurantMenuRepository.findById(menuId)
//                .orElseThrow(() -> new RuntimeException("❌ Không tìm thấy menu ID: " + menuId));
//
//        // Kiểm tra xem menu có thuộc nhà hàng này không
//        if (Integer.valueOf(menu.getRestaurant().getRestaurantId()).equals(restaurantId)) {
//            throw new RuntimeException("❌ Menu ID " + menuId + " không thuộc nhà hàng ID: " + restaurantId);
//        }
//
//        // Cập nhật thông tin menu
//        menu.setActive(menuDTO.isActive());
//
//        // Xóa danh sách món ăn cũ trong menu
//        restaurantMenuItemRepository.deleteByRestaurantMenu_RestaurantMenuId(menuId);
//
//        // Thêm danh sách món ăn mới vào menu
//        List<RestaurantMenuItem> newMenuItems = menuDTO.getMenuItems().stream()
//                .map(itemDTO -> {
//                    Food food = foodRepository.findById(itemDTO.getFoodId())
//                            .orElseThrow(() -> new RuntimeException("❌ Không tìm thấy Food ID: " + itemDTO.getFoodId()));
//
//                    return RestaurantMenuItem.builder()
//                            .restaurantMenu(menu)
//                            .food(food)
//                            .price(itemDTO.getPrice())
//                            .build();
//                }).collect(Collectors.toList());
//
//        menu.setMenuItems(newMenuItems);
//        restaurantMenuItemRepository.saveAll(newMenuItems);
//
//        // Lưu menu đã cập nhật
//        RestaurantMenu updatedMenu = restaurantMenuRepository.save(menu);
//
//        // Trả về DTO sau khi cập nhật
//        return RestaurantMenuDTO.builder()
//                .id(updatedMenu.getRestaurantMenuId())
//                .isActive(updatedMenu.isActive())
//                .menuItems(updatedMenu.getMenuItems().stream()
//                        .map(restaurantMenuItemMapper::toDTO)
//                        .collect(Collectors.toList()))
//                .build();
//    }
}






