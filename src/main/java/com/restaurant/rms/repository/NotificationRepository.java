package com.restaurant.rms.repository;

import com.restaurant.rms.entity.Notification;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> { // Thay Integer thành Long
    @Query("SELECT n FROM Notification n WHERE n.user.user_id = :userId")
    List<Notification> findNotificationsByUserId(@Param("userId") Long userId);
}