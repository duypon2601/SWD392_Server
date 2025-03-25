package com.restaurant.rms.repository;

import com.restaurant.rms.entity.NotificationEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {

    // Lấy danh sách thông báo theo userId
    @Query("SELECT n FROM NotificationEntity n WHERE n.user.user_id = :userId")
    List<NotificationEntity> findByUser_UserId(@Param("userId") int userId);

    // Lấy danh sách thông báo đã xóa mềm
    @Query(value = "SELECT * FROM notifications WHERE is_deleted = true", nativeQuery = true)
    List<NotificationEntity> findAllDeleted();

    // Tìm thông báo đã xóa mềm theo ID
    @Query(value = "SELECT * FROM notifications WHERE notification_id = ?1 AND is_deleted = true", nativeQuery = true)
    Optional<NotificationEntity> findDeletedById(int id);
}