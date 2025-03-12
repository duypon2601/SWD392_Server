package com.restaurant.rms.repository;

import com.restaurant.rms.entity.Food;
import com.restaurant.rms.entity.RestaurantMenu;
import com.restaurant.rms.entity.RestaurantMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Integer> {
    List<RestaurantMenuItem> findByRestaurantMenu_RestaurantMenuId(int restaurantMenuId);
    void deleteByFood_foodId(Integer foodId);
    void deleteByRestaurantMenu_RestaurantMenuId(Integer restaurantMenuId);
    boolean existsByRestaurantMenuAndFood(RestaurantMenu restaurantMenu, Food food);
}
