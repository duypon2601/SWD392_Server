package com.restaurant.rms.repository;

import com.restaurant.rms.entity.SubOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubOrderItemRepository extends JpaRepository<SubOrderItem, Integer> {
}
