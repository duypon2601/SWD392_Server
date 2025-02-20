package com.restaurant.rms.service.RestaurantMenuItemService;

import com.restaurant.rms.dto.request.RestaurantMenuItemDTO;
import com.restaurant.rms.entity.Food;
import com.restaurant.rms.entity.Restaurant;
import com.restaurant.rms.entity.RestaurantMenuItem;
import com.restaurant.rms.mapper.RestaurantMapper;
import com.restaurant.rms.mapper.RestaurantMenuItemMapper;
import com.restaurant.rms.repository.FoodRepository;
import com.restaurant.rms.repository.RestaurantMenuItemRepository;
import com.restaurant.rms.repository.RestaurantRepository;
import com.restaurant.rms.util.error.IdInvalidException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantMenuItemServiceImlp implements RestaurantMenuItemService{

    private RestaurantMenuItemRepository restaurantMenuItemRepository;
    private RestaurantRepository restaurantRepository;
    private FoodRepository foodRepository;
    @Override
    public RestaurantMenuItemDTO createRestaurantMenuItem(RestaurantMenuItemDTO restaurantMenuItemDTO) throws IdInvalidException{
        Restaurant restaurant = restaurantRepository.findById(restaurantMenuItemDTO.getRestaurant_id())
                .orElseThrow(() -> new IdInvalidException("Restaurant not found"));

        Food food = foodRepository.findById(restaurantMenuItemDTO.getFood_id())
                .orElseThrow(() -> new IdInvalidException("Food not found"));
        RestaurantMenuItem restaurantMenuItem = RestaurantMenuItemMapper.mapToRestaurantMenuItem(restaurantMenuItemDTO);
        restaurantMenuItem.setRestaurant(restaurant);
        restaurantMenuItem.setFood(food);
        restaurantMenuItem.setPrice(restaurantMenuItemDTO.getPrice());
        restaurantMenuItem.setStock_quantity(restaurantMenuItemDTO.getStock_quantity());
        restaurantMenuItem.setMin_stock_threshold(restaurantMenuItemDTO.getMin_stock_threshold());

        RestaurantMenuItem savedRestaurantMenuItem = restaurantMenuItemRepository.save(restaurantMenuItem);

        return RestaurantMenuItemMapper.mapToRestaurantMenuItemDTO(savedRestaurantMenuItem);
    }

    @Override
    public RestaurantMenuItemDTO getRestaurantMenuItemById ( Integer menu_item_id) throws IdInvalidException{
        Optional<RestaurantMenuItem> restaurantMenuItem = restaurantMenuItemRepository.findById(menu_item_id);
        if (restaurantMenuItem.isPresent()) {
            return RestaurantMenuItemMapper.mapToRestaurantMenuItemDTO(restaurantMenuItem.get());
        }else {
            throw new IdInvalidException("Menu với id = " + menu_item_id + " không tồn tại");
        }
    }
//    @Override
//    public RestaurantDTO findRestaurantByUserId(int user_id) throws IdInvalidException {
//        Restaurant restaurant = restaurantRepository.findRestaurantByUserId(user_id);
//
//        if (restaurant == null) {
//            throw new IdInvalidException("Restaurant not found for user ID: " + user_id);
//        }
//
//        return RestaurantMapper.mapToRestaurantDTO(restaurant);
//    }


    @Override
    public List<RestaurantMenuItemDTO> getRestaurantMenuItemAll(){
        List<RestaurantMenuItem> restaurantMenuItem = restaurantMenuItemRepository.findAll();
        return restaurantMenuItem.stream().map(
                (restaurantMenuItems) -> RestaurantMenuItemMapper.mapToRestaurantMenuItemDTO(restaurantMenuItems)).collect(Collectors.toList()
        );
    }

    @Override
    public RestaurantMenuItemDTO updateRestaurantMenuItem (RestaurantMenuItemDTO restaurantDTO, Integer menu_item_id){
        RestaurantMenuItem restaurantMenuItem = restaurantMenuItemRepository.findById(menu_item_id)
                .orElseThrow(()-> new RuntimeException("Menu "+menu_item_id+" not found"));

        restaurantDTO.setPrice(restaurantDTO.getPrice());
        restaurantDTO.setStock_quantity(restaurantDTO.getStock_quantity());
        restaurantDTO.setMin_stock_threshold(restaurantDTO.getMin_stock_threshold());

        RestaurantMenuItem updateRestaurantMenuItemObj = restaurantMenuItemRepository.save(restaurantMenuItem);
        return RestaurantMenuItemMapper.mapToRestaurantMenuItemDTO(updateRestaurantMenuItemObj);
    }

    @Override
    public void deleteRestaurantMenuItem (Integer menu_item_id) throws IdInvalidException{
        Restaurant restaurant = restaurantRepository.findById(menu_item_id)
                .orElseThrow(() -> new IdInvalidException("Menu với id = " + menu_item_id + " không tồn tại"));
        restaurantRepository.deleteById(menu_item_id);
    }
}
