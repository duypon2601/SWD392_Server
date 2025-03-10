package com.restaurant.rms.service.retaurantService;


import com.restaurant.rms.dto.request.RestaurantDTO;
import com.restaurant.rms.entity.Restaurant;
import com.restaurant.rms.mapper.RestaurantMapper;
import com.restaurant.rms.repository.RestaurantRepository;
import com.restaurant.rms.repository.UserRepository;
import com.restaurant.rms.util.error.IdInvalidException;
import com.restaurant.rms.util.error.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private RestaurantRepository restaurantRepository;

    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) throws IdInvalidException {
        if (restaurantRepository.existsByName(restaurantDTO.getName())) {
            throw new IdInvalidException("Username " + restaurantDTO.getName() + " đã tồn tại, vui lòng sử dụng email khác.");
        }

        // Chuyển DTO thành Entity
        Restaurant restaurant = RestaurantMapper.mapToRestaurant(restaurantDTO);


        // Lưu vào database, Hibernate sẽ tự động sinh ID
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        // Trả về DTO đã có ID từ DB
        return RestaurantMapper.mapToRestaurantDTO(savedRestaurant);
    }

    @Override
    public RestaurantDTO getRestaurantById( Integer restaurantId) throws IdInvalidException {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            return RestaurantMapper.mapToRestaurantDTO(restaurant.get());
        }else {
            throw new IdInvalidException("Restaurant với id = " + restaurantId + " không tồn tại");
        }
    }
    @Override
    public RestaurantDTO findRestaurantByUserId(int userId) throws IdInvalidException {
        Restaurant restaurant = restaurantRepository.findRestaurantByUserId(userId);

        if (restaurant == null) {
            throw new IdInvalidException("Restaurant not found for user ID: " + userId);
        }

        return RestaurantMapper.mapToRestaurantDTO(restaurant);
    }


    @Override
    public List<RestaurantDTO> getRestaurantAll() {
        List<Restaurant> restaurant = restaurantRepository.findAll();
        return restaurant.stream().map(
                (restaurants) -> RestaurantMapper.mapToRestaurantDTO(restaurants)).collect(Collectors.toList()
        );
    }

    @Override
    public RestaurantDTO updateRestaurant(RestaurantDTO restaurantDTO, Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant " + restaurantId + " not found"));
        restaurant.setName(restaurantDTO.getName());
        restaurant.setLocation(restaurantDTO.getLocation());
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantMapper.mapToRestaurantDTO(updatedRestaurant);
    }

    @Override
    public void deleteRestaurant(Integer restaurant_id) throws IdInvalidException{
        Restaurant restaurant = restaurantRepository.findById(restaurant_id)
                .orElseThrow(() -> new IdInvalidException("Restaurant với id = " + restaurant_id + " không tồn tại"));
        restaurantRepository.deleteById(restaurant_id);
    }
//    @Override
//    public List<CourseDTO> getCoursesBoughtByParent(int user_id) {
//        List<Course> courses = courseRepository.findCoursesBoughtByParent(user_id);
//        return courses.stream()
//                .map(CourseMapper::mapToCourseDTO)
//                .collect(Collectors.toList());
//    }
//    @Override
//    public List<CourseDTO> getCoursesNotBoughtByParent(int user_id) {
//        List<Course> courses = courseRepository.findCoursesNotBoughtByParent(user_id);
//        return courses.stream()
//                .map(CourseMapper::mapToCourseDTO)
//                .collect(Collectors.toList());
//    }
//    public List<CourseDTO> getCourseByCategoryId(int category_id) throws IdInvalidException {
//        List<Course> courses = courseRepository.findCourseByCategoryId(category_id);
//        CategoryDTO categoryDTO = categoryService.getCategoryById(category_id);
//        if (categoryDTO == null) {
//            throw new IdInvalidException("Trong course id = " + category_id + " hiện không có chapter");
//        }
//        return courses.stream().map(
//                (course) -> CourseMapper.mapToCourseDTO(course)).collect(Collectors.toList()
//        );
//    }

}
