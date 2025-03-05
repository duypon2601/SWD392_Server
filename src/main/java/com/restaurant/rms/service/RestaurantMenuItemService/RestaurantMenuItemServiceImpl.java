//package com.restaurant.rms.service.RestaurantMenuItemService;
//
//import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
//import com.restaurant.rms.entity.RestaurantMenu;
//import com.restaurant.rms.entity.RestaurantMenuItem;
//import com.restaurant.rms.mapper.RestaurantMenuItemMapper;
//import com.restaurant.rms.repository.RestaurantMenuItemRepository;
//import com.restaurant.rms.repository.RestaurantMenuRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class RestaurantMenuItemServiceImpl implements RestaurantMenuItemService {
//
//    @Autowired
//    private RestaurantMenuItemRepository restaurantMenuItemRepository;
//
//    @Autowired
//    private RestaurantMenuRepository restaurantMenuRepository;
//
//    @Autowired
//    private RestaurantMenuItemMapper restaurantMenuItemMapper;
//
//
//    @Override
//    public RestaurantMenuItemDTO addMenuItemToMenu(int menuId, RestaurantMenuItemDTO menuItemDTO) {
//        RestaurantMenu restaurantMenu = restaurantMenuRepository.findById(menuId)
//                .orElseThrow(() -> new EntityNotFoundException("Menu not found"));
//
//        RestaurantMenuItem menuItem = restaurantMenuItemMapper.toEntity(menuItemDTO);
//        menuItem.setRestaurantMenu(restaurantMenu);
//        menuItem = restaurantMenuItemRepository.save(menuItem);
//        return restaurantMenuItemMapper.toDTO(menuItem);
//    }
//
//    @Override
//    public RestaurantMenuItemDTO updateMenuItem(int id, RestaurantMenuItemDTO menuItemDTO) {
//        RestaurantMenuItem menuItem = restaurantMenuItemRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Menu item not found"));
//
//        menuItem.setPrice(menuItemDTO.getPrice());
//        menuItem.setStockQuantity(menuItemDTO.getStockQuantity());
//        menuItem.setMinStockThreshold(menuItemDTO.getMinStockThreshold());
//        menuItem.setAvailable(menuItemDTO.isAvailable());
//
//        menuItem = restaurantMenuItemRepository.save(menuItem);
//        return restaurantMenuItemMapper.toDTO(menuItem);
//    }
//
//    @Override
//    public void removeMenuItem(int id) {
//        restaurantMenuItemRepository.deleteById(id);
//    }
//
//    @Override
//    public RestaurantMenuItemDTO getMenuItemById(int id) {
//        return restaurantMenuItemRepository.findById(id)
//                .map(restaurantMenuItemMapper::toDTO)
//                .orElseThrow(() -> new EntityNotFoundException("Menu item not found"));
//    }
//
//    @Override
//    public List<RestaurantMenuItemDTO> getAllMenuItemsByMenu(int menuId) {
//        return restaurantMenuItemRepository.findByRestaurantMenu_RestaurantMenuId(menuId).stream()
//                .map(restaurantMenuItemMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//}
//
