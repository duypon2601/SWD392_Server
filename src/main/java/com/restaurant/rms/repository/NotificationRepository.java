package com.restaurant.rms.repository;

import com.restaurant.rms.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByIsReadFalse(); // Lấy các thông báo chưa đọc
    List<Notification> findByIsReadTrue();  // Lấy các thông báo đã đọc (nếu cần)
}