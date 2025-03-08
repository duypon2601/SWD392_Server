package com.restaurant.rms.repository;

import com.restaurant.rms.entity.Order;
import com.restaurant.rms.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByDiningTable_DiningTableIdAndStatus(int diningTableId, OrderStatus status);
    boolean existsByDiningTable_DiningTableIdAndStatusIn(int diningTableId, List<OrderStatus> statuses);
}
