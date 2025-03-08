package com.restaurant.rms.service.retaurantService;

import com.restaurant.rms.dto.request.FoodDTO;
import com.restaurant.rms.dto.request.RestaurantDTO;
import com.restaurant.rms.dto.request.UserDTO;
import com.restaurant.rms.util.error.IdInvalidException;

import java.util.List;

public interface RestaurantService {
    RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) throws IdInvalidException;

    RestaurantDTO getRestaurantById ( Integer restaurant_id) throws IdInvalidException;

    RestaurantDTO findRestaurantByUserId(int user_id) throws IdInvalidException;

    List<RestaurantDTO> getRestaurantAll();

    RestaurantDTO updateRestaurant (RestaurantDTO restaurantDTO, Integer restaurant_id);

    void deleteRestaurant (Integer restaurantId) throws IdInvalidException;
//
//    List<CourseDTO> getCoursesBoughtByParent(int userId);
//
//    List<CourseDTO> getCoursesNotBoughtByParent(int userId);
//
//    List<CourseDTO> getCourseByCategoryId(int category_id) throws IdInvalidException;
}
