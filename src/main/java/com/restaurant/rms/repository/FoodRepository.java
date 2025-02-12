package com.restaurant.rms.repository;

import com.restaurant.rms.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository  extends JpaRepository<Food,Integer> {
    Optional<Food> findByName(String name);
    boolean existsByName(String name);
}
