package com.restaurant.rms.controller;


import com.restaurant.rms.dto.request.RestaurantDTO;
import com.restaurant.rms.service.retaurantService.RestaurantService;
import com.restaurant.rms.util.error.IdInvalidException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/restaurant")
@SecurityRequirement(name = "api")
public class RestaurantController {
    private RestaurantService restaurantService;

    @PostMapping("/create")
//    @PreAuthorize("hasRole('CONTENT_MANAGER')")
    public ResponseEntity<RestaurantDTO> createRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) throws IdInvalidException {
        RestaurantDTO savedRestaurant = restaurantService.createRestaurant(restaurantDTO);
        return ResponseEntity.status(HttpStatus.OK).body(savedRestaurant);
    }

    @GetMapping("{restaurant_id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById (@PathVariable("restaurant_id") Integer restaurant_id) throws IdInvalidException {
        RestaurantDTO restaurantDTO = restaurantService.getRestaurantById(restaurant_id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }

    @GetMapping("/get")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantAll(){
        List<RestaurantDTO> restaurants = restaurantService.getRestaurantAll();
        return ResponseEntity.ok(restaurants);
    }

    @PutMapping("/{restaurant_id}")
//    @PreAuthorize("hasAnyRole('CONTENT_MANAGER', 'MANAGER')")
    public ResponseEntity<RestaurantDTO> updateRestaurant (@RequestBody RestaurantDTO updatedRestaurant, @PathVariable("restaurant_id") Integer restaurant_id){
        RestaurantDTO restaurantDTO = restaurantService.updateRestaurant(updatedRestaurant, restaurant_id);
        return ResponseEntity.ok(restaurantDTO);
    }

    @DeleteMapping("{restaurant_id}")
//    @PreAuthorize("hasAnyRole('CONTENT_MANAGER', 'MANAGER')")
    public ResponseEntity<Void> deleteRestaurant( @PathVariable("restaurant_id") Integer restaurant_id) throws IdInvalidException {
        RestaurantDTO currentRestaurant = restaurantService.getRestaurantById(restaurant_id);
        restaurantService.deleteRestaurant(restaurant_id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("user/{user_id}")
    public ResponseEntity<RestaurantDTO> findRestaurantByUserId(@PathVariable("user_id") int user_id) throws IdInvalidException {
        RestaurantDTO restaurantDTO = restaurantService.findRestaurantByUserId(user_id);
        return ResponseEntity.ok(restaurantDTO);
    }
//    @GetMapping("/bought/{user_id}")
//    @PreAuthorize("hasRole('PARENT')") // can xem lai
//    public ResponseEntity<List<CourseDTO>> getCoursesBoughtByParent(@PathVariable("user_id") int user_id) {
//        List<CourseDTO> courses = courseService.getCoursesBoughtByParent(user_id);
//        return ResponseEntity.ok(courses);
//    }
//
//    @GetMapping("/notbought/{user_id}")
//    @PreAuthorize("hasRole('PARENT')")
//    public ResponseEntity<List<CourseDTO>> getCoursesNotBoughtByParent(@PathVariable("user_id") int user_id) {
//        List<CourseDTO> courses = courseService.getCoursesNotBoughtByParent(user_id);
//        return ResponseEntity.ok(courses);
//    }
//
//    @GetMapping("/category/{category_id}")
//
//    public ResponseEntity<List<CourseDTO>> getCourseByCategoryId(@PathVariable("category_id") int category_id) throws IdInvalidException {
//        List<CourseDTO> courseDTOS = courseService.getCourseByCategoryId(category_id);
//        return ResponseEntity.ok(courseDTOS);
//    }

}
