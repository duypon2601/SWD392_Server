package com.restaurant.rms.repository;

import com.restaurant.rms.entity.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DiningTableRepository extends JpaRepository<DiningTable, Integer> {
    Optional<DiningTable> findByQrCode(String qrCode);
    List<DiningTable> findByRestaurant_RestaurantId(int restaurantId);

    // Lấy danh sách bản ghi đã xóa mềm
    @Query(value = "SELECT * FROM dining_table WHERE is_deleted = true", nativeQuery = true)
    List<DiningTable> findAllDeleted();

    // Tìm bản ghi đã xóa mềm theo ID
    @Query(value = "SELECT * FROM dining_table WHERE dining_table_id = ?1 AND is_deleted = true", nativeQuery = true)
    Optional<DiningTable> findDeletedById(int id);
}