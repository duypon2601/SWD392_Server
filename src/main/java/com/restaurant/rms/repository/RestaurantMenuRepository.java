package com.restaurant.rms.repository;

import com.restaurant.rms.entity.Restaurant;
import com.restaurant.rms.entity.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Integer> {
    List<RestaurantMenu> findByRestaurant_RestaurantId(int restaurantId);
    boolean existsByRestaurant(Restaurant restaurant);
}






