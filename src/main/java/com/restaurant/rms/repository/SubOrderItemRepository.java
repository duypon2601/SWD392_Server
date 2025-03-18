package com.restaurant.rms.repository;

import com.restaurant.rms.entity.SubOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubOrderItemRepository extends JpaRepository<SubOrderItem, Integer> {
    List<SubOrderItem> findByMenuItem_RestaurantMenuItemId(int menuItemId);
    void deleteByMenuItem_RestaurantMenuItemId(int menuItemId);
}
