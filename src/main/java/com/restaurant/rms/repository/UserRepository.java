package com.restaurant.rms.repository;



import com.restaurant.rms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.restaurant.restaurantId = :restaurantId")
    List<User> findUserByRestaurantId(@Param("restaurantId") int restaurantId);

    // Lấy danh sách user đã xóa mềm bằng native query
    @Query(value = "SELECT * FROM user WHERE is_deleted = true", nativeQuery = true)
    List<User> findAllByIsDeletedTrue();

    // Tìm user đã xóa mềm theo ID bằng native query
    @Query(value = "SELECT * FROM user WHERE user_id = :userId AND is_deleted = true", nativeQuery = true)
    Optional<User> findByIdAndIsDeletedTrue(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.restaurant.restaurantId = :restaurantId")
    void softDeleteUsersByRestaurantId(@Param("restaurantId") int restaurantId);
}
