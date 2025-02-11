package com.restaurant.rms.repository;

import com.restaurant.rms.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository  extends JpaRepository<Food,Integer> {
//    Optional<Food> FindByName(String name);
//    boolean existsByName(String Name);
}
