package com.restaurant.rms.repository;


import com.restaurant.rms.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    Optional<Restaurant> findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT l.restaurant FROM User l WHERE l.user_id = :user_id")
    Restaurant findRestaurantByUserId(@Param("user_id") int user_id);
}
