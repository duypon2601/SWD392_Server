package com.restaurant.rms.repository;

import com.restaurant.rms.entity.Restaurant;
import com.restaurant.rms.entity.RestaurantMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem,Integer> {
//    Optional<RestaurantMenuItem> findByName(String name);
//    boolean existsByName(String name);


}
