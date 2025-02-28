//package com.restaurant.rms.repository;
//
//import com.restaurant.rms.entity.DiningTable;
//import com.restaurant.rms.entity.Order;
//import com.restaurant.rms.enums.OrderStatus;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface OrderRepository extends JpaRepository<Order, Integer> {
//    Optional<Order> findByDiningTableAndStatus(DiningTable diningTable, OrderStatus status);
//    Optional<Order> findByDiningTableIdAndStatus(int diningTableId, OrderStatus status);
//}
