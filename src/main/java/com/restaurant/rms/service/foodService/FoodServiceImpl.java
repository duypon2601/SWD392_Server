package com.restaurant.rms.service.foodService;

import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.entity.Food;
import com.restaurant.rms.mapper.FoodMapper;
import com.restaurant.rms.repository.FoodRepository;
import com.restaurant.rms.util.error.IdInvalidException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoodServiceImpl implements FoodService {
    private FoodRepository foodRepository;


    @Override
    public FoodDTO createFood(FoodDTO foodDTO) throws IdInvalidException {
        if (foodRepository.existsByName(foodDTO.getName())) {
            throw new IdInvalidException("Name " + foodDTO.getName() + " đã tồn tại, vui lòng sử dụng tên khác.");
        }
        Food food = FoodMapper.mapToFood(foodDTO);
        food.setStatus(foodDTO.getStatus());
        Food savedFood= foodRepository.save(food);
        return FoodMapper.mapToFoodDTO(savedFood);
    }

    @Override
    public FoodDTO getFoodById(Integer food_id) throws IdInvalidException {
        return null;
    }

    @Override
    public List<FoodDTO> getFoodAll() {
        return List.of();
    }

    @Override
    public FoodDTO updateFood(FoodDTO updateFood, Integer food_id) {
        return null;
    }

    @Override
    public void deleteFood(Integer food_id) throws IdInvalidException {

    }

    @Override
    public FoodDTO handleGetFoodByName(String name) {
        return null;
    }

    @Override
    public boolean isFoodNameExist(String name) {
        return false;
    }
}
