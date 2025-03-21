package com.restaurant.rms.repository;


import com.restaurant.rms.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    Optional<Restaurant> findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT l.restaurant FROM User l WHERE l.user_id = :user_id")
    Restaurant findRestaurantByUserId(@Param("user_id") int user_id);

    // Lấy danh sách nhà hàng đã xóa mềm bằng native query
    @Query(value = "SELECT * FROM restaurant WHERE is_deleted = true", nativeQuery = true)
    List<Restaurant> findAllByIsDeletedTrue();

    // Tìm nhà hàng đã xóa mềm theo ID bằng native query
    @Query(value = "SELECT * FROM restaurant WHERE restaurant_id = :id AND is_deleted = true", nativeQuery = true)
    Optional<Restaurant> findByIdAndIsDeletedTrue(@Param("id") Integer id);
}
