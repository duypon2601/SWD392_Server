package com.restaurant.rms.repository;

import com.restaurant.rms.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByMenuItem_RestaurantMenuItemId(int menuItemId);
    void deleteByMenuItem_RestaurantMenuItemId(int menuItemId);
}
