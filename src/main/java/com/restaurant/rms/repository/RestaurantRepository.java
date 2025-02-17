package com.restaurant.rms.repository;


import com.restaurant.rms.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    Optional<Restaurant> findByName(String name);
    boolean existsByName(String name);
}
