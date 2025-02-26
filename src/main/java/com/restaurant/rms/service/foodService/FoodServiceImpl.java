package com.restaurant.rms.service.foodService;

import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.entity.Food;
import com.restaurant.rms.mapper.FoodMapper;
import com.restaurant.rms.repository.FoodRepository;
import com.restaurant.rms.util.error.IdInvalidException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

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
//        existingFood.setCategory_id(updateFood.getCategory_id());
        existingFood.setStatus(updateFood.getStatus());

        Food updatedFood = foodRepository.save(existingFood);
        return FoodMapper.mapToFoodDTO(updatedFood);
    }

    @Override
    public void deleteFood(Integer foodId) throws IdInvalidException {
        if (!foodRepository.existsById(foodId)) {
            throw new IdInvalidException("Food ID not found");
        }
        foodRepository.deleteById(foodId);
    }
}
