package com.restaurant.rms.repository;

import com.restaurant.rms.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FoodRepository  extends JpaRepository<Food,Integer> {
    Optional<Food> findByName(String name);
    boolean existsByName(String name);
        @Query("SELECT DISTINCT f FROM Food f " +
                "JOIN RestaurantMenuItem rmi ON f.id = rmi.food.id " +
                "WHERE rmi.restaurant.id = :restaurantId")
        List<Food> findFoodsByRestaurantId(@Param("restaurantId") int restaurantId);

}
