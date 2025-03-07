//package com.restaurant.rms.service.RestaurantMenuItemService;
//
//import com.restaurant.rms.dto.request.CreateRestaurantMenuItemDTO;
//import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
//import com.restaurant.rms.dto.request.UpdateRestaurantMenuItemDTO;
//import com.restaurant.rms.entity.Food;
//import com.restaurant.rms.entity.RestaurantMenu;
//import com.restaurant.rms.entity.RestaurantMenuItem;
//import com.restaurant.rms.mapper.RestaurantMenuItemMapper;
//import com.restaurant.rms.repository.FoodRepository;
//import com.restaurant.rms.repository.RestaurantMenuItemRepository;
//import com.restaurant.rms.repository.RestaurantMenuRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class RestaurantMenuItemServiceImpl implements RestaurantMenuItemService {
//    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
//    private final RestaurantMenuRepository restaurantMenuRepository;
//    private final FoodRepository foodRepository;
//    private final RestaurantMenuItemMapper restaurantMenuItemMapper;
//
//    @Override
//    @Transactional
//    public RestaurantMenuItemDTO createRestaurantMenuItem(CreateRestaurantMenuItemDTO menuItemDTO) {
//        RestaurantMenu menu = restaurantMenuRepository.findById(menuItemDTO.getMenuId())
//                .orElseThrow(() -> new RuntimeException("Menu not found"));
//
//        Food food = foodRepository.findById(menuItemDTO.getFoodId())
//                .orElseThrow(() -> new RuntimeException("Food not found"));
//
//        RestaurantMenuItem newItem = new RestaurantMenuItem();
//        newItem.setMenu(menu);
//        newItem.setFood(food);
//        newItem.setPrice(menuItemDTO.getPrice());
//        newItem.setQuantity(menuItemDTO.getQuantity());
//        newItem.setMinStock(10);
//        newItem.setAvailable(true);
//
//        return restaurantMenuItemMapper.toDTO(restaurantMenuItemRepository.save(newItem));
//    }
//
//    @Override
//    public RestaurantMenuItemDTO getRestaurantMenuItemById(Long id) {
//        RestaurantMenuItem item = restaurantMenuItemRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Menu Item not found"));
//        return restaurantMenuItemMapper.toDTO(item);
//    }
//
//    @Override
//    public List<RestaurantMenuItemDTO> getAllRestaurantMenuItems() {
//        return restaurantMenuItemRepository.findAll().stream()
//                .map(restaurantMenuItemMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional
//    public RestaurantMenuItemDTO updateRestaurantMenuItem(Long id, UpdateRestaurantMenuItemDTO menuItemDTO) {
//        RestaurantMenuItem item = restaurantMenuItemRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Menu Item not found"));
//
//        item.setPrice(menuItemDTO.getPrice());
//        item.setStockQuantity(menuItemDTO.getQuantity());
//        item.setAvailable(menuItemDTO.getIsAvailable());
//
//        return restaurantMenuItemMapper.toDTO(restaurantMenuItemRepository.save(item));
//    }
//
//    @Override
//    @Transactional
//    public void deleteRestaurantMenuItem(Long id) {
//        RestaurantMenuItem item = restaurantMenuItemRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Menu Item not found"));
//        restaurantMenuItemRepository.delete(item);
//    }
//}
