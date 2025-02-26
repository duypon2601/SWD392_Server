package com.restaurant.rms.repository;



import com.restaurant.rms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query("SELECT l FROM User l WHERE l.restaurant.restaurant_id = :restaurant_id")
    List<User> findUserByRestaurantId(@Param(value = "restaurant_id") int restaurant_id);

//    @Query("SELECT u FROM User u JOIN u.payments p WHERE p.payment_date BETWEEN :startDate AND :endDate")
//    List<User> findUsersByPaymentDates(String startDate, String endDate);


}
