package com.restaurant.rms.service.foodService;

import com.restaurant.rms.dto.request.CreateFoodDTO;
import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.entity.Category;
import com.restaurant.rms.entity.Food;
import com.restaurant.rms.mapper.FoodMapper;
import com.restaurant.rms.repository.CategoryRepository;
import com.restaurant.rms.repository.FoodRepository;
import com.restaurant.rms.repository.RestaurantMenuItemRepository;
import com.restaurant.rms.util.error.IdInvalidException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final RestaurantMenuItemRepository restaurantMenuItemRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public FoodDTO createFood(CreateFoodDTO createFoodDTO) throws IdInvalidException {
        Category category = categoryRepository.findById(createFoodDTO.getCategory_id())
                .orElseThrow(() -> new IdInvalidException("Category ID không tồn tại"));

        // Map CreateFoodDTO sang Food
        Food food = FoodMapper.mapToFood(createFoodDTO);
        food.setDeleted(false); // Đảm bảo isDeleted là false khi tạo mới
        food.setCategory(category); // Gán Category vào Food

        // Lưu Food
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

        Category category = categoryRepository.findById(updateFood.getCategory_id())
                .orElseThrow(() -> new IdInvalidException("Category ID không hợp lệ"));
        existingFood.setName(updateFood.getName());
        existingFood.setDescription(updateFood.getDescription());
        existingFood.setImage_url(updateFood.getImage_url());
        existingFood.setStatus(updateFood.getStatus());
        existingFood.setCategory(category);

        Food updatedFood = foodRepository.save(existingFood);
        return FoodMapper.mapToFoodDTO(updatedFood);
    }

    @Override
    @Transactional
    public void deleteFood(Integer foodId) throws IdInvalidException {
        // Kiểm tra xem Food có tồn tại không
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IdInvalidException("Food ID not found"));

        // Đánh dấu xóa mềm cho Food
        food.setDeleted(true);
        foodRepository.save(food);
        log.info("Food ID {} đã được đánh dấu xóa mềm", foodId);

        // Xóa mềm hoặc xóa cứng các RestaurantMenuItem liên quan
        restaurantMenuItemRepository.deleteByFood_foodId(foodId);
        log.info("Đã xóa tất cả mục menu liên quan đến Food ID {}", foodId);
    }
//    xóa vĩnh viễn food
//    @Override
//    @Transactional
//    public void deleteFood(Integer foodId) throws IdInvalidException {
//        // Kiểm tra xem Food có tồn tại không
//        Food food = foodRepository.findById(foodId)
//                .orElseThrow(() -> new IdInvalidException("Food ID not found"));
//
//        // Xóa tất cả RestaurantMenuItem liên quan đến món ăn này
//        restaurantMenuItemRepository.deleteByFood_foodId(foodId);
//        log.info("Đã xóa tất cả mục menu liên quan đến Food ID {}", foodId);
//
//        // Xóa luôn món ăn
//        foodRepository.delete(food);
//        log.info("Food ID {} đã bị xóa khỏi hệ thống", foodId);
//    }

    @Override
    public List<FoodDTO> getAllDeletedFood() {
        log.info("Bắt đầu lấy danh sách Food đã bị xóa mềm");
        List<Food> deletedFoods = foodRepository.findAllDeleted();

        if (deletedFoods == null || deletedFoods.isEmpty()) {
            log.info("Không tìm thấy Food nào đã bị xóa mềm");
            return Collections.emptyList();
        }

        List<FoodDTO> foodDTOs = deletedFoods.stream()
                .map(FoodMapper::mapToFoodDTO)
                .collect(Collectors.toList());

        log.info("Tìm thấy {} Food đã bị xóa mềm", foodDTOs.size());
        return foodDTOs;
    }

    @Override
    @Transactional
    public FoodDTO restoreFood(Integer foodId) throws IdInvalidException {
        Food food = foodRepository.findByIdIncludingDeleted(foodId)
                .orElseThrow(() -> new IdInvalidException("Food ID not found"));

        if (!food.isDeleted()) {
            throw new IdInvalidException("Food ID " + foodId + " chưa bị xóa mềm, không thể khôi phục");
        }

        food.setDeleted(false);
        Food restoredFood = foodRepository.save(food);
        log.info("Food ID {} đã được khôi phục", foodId);
        return FoodMapper.mapToFoodDTO(restoredFood);
    }

}
