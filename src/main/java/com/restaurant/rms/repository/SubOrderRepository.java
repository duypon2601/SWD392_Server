package com.restaurant.rms.repository;

import com.restaurant.rms.entity.SubOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubOrderRepository extends JpaRepository<SubOrder, Integer> {
    // Thêm phương thức: Tìm danh sách SubOrder theo Order ID
    List<SubOrder> findByOrder_OrderId(int orderId);


}
