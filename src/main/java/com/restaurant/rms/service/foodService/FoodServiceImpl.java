package com.restaurant.rms.service.foodService;

import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.entity.Food;
import com.restaurant.rms.mapper.FoodMapper;
import com.restaurant.rms.repository.FoodRepository;
import com.restaurant.rms.repository.RestaurantMenuItemRepository;
import com.restaurant.rms.util.error.IdInvalidException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;

    @Override
    public FoodDTO createFood(FoodDTO foodDTO) throws IdInvalidException {
        Food food = FoodMapper.mapToFood(foodDTO);
        Food savedFood = foodRepository.save(food);
        return FoodMapper.mapToFoodDTO(savedFood);
    }

    @Override
    public FoodDTO getFoodById(Integer foodId) throws IdInvalidException {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IdInvalidException("Food ID not found"));
        return FoodMapper.mapToFoodDTO(food);
    }

    @Override
    public List<FoodDTO> getAllFood() {
        return foodRepository.findAll()
                .stream()
                .map(FoodMapper::mapToFoodDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FoodDTO updateFood(FoodDTO updateFood, Integer foodId) throws IdInvalidException {
        Food existingFood = foodRepository.findById(foodId)
                .orElseThrow(() -> new IdInvalidException("Food ID not found"));
        existingFood.setName(updateFood.getName());
        existingFood.setDescription(updateFood.getDescription());
        existingFood.setImage_url(updateFood.getImage_url());
        existingFood.setStatus(updateFood.getStatus());

        Food updatedFood = foodRepository.save(existingFood);
        return FoodMapper.mapToFoodDTO(updatedFood);
    }

    @Override
    @Transactional
    public void deleteFood(Integer foodId) throws IdInvalidException {
        // Kiểm tra xem Food có tồn tại không
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IdInvalidException("Food ID not found"));

        // Xóa tất cả RestaurantMenuItem liên quan đến món ăn này
        restaurantMenuItemRepository.deleteByFood_foodId(foodId);
        log.info("Đã xóa tất cả mục menu liên quan đến Food ID {}", foodId);

        // Xóa luôn món ăn
        foodRepository.delete(food);
        log.info("Food ID {} đã bị xóa khỏi hệ thống", foodId);
    }


//    @Override
//    public List<FoodDTO> getFoodsByRestaurant(int restaurantId) {
//        List<Food> foods = foodRepository.findFoodsByRestaurantId(restaurantId);
//        return foods.stream()
//                .map(FoodMapper::mapToFoodDTO)
//                .collect(Collectors.toList());
//    }
}
