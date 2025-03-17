package com.restaurant.rms.repository;

import com.restaurant.rms.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT p FROM Payment p WHERE p.order.orderId = :orderId")
    List<Payment> findByOrderId(Integer orderId);
}