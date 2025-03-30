package com.restaurant.rms.repository;

import com.restaurant.rms.entity.Order;
import com.restaurant.rms.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByDiningTable_DiningTableIdAndStatus(int diningTableId, OrderStatus status);
    boolean existsByDiningTable_DiningTableIdAndStatusIn(int diningTableId, List<OrderStatus> statuses);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.orderId = :orderId AND o.status = 'COMPLETED'")
    Optional<Order> findCompletedOrderById(@Param("orderId") Integer orderId);

    // Lấy tất cả Order nhưng chỉ những bàn chưa bị xóa mềm
    @Query("SELECT o FROM Order o WHERE o.diningTable.isDeleted = false")
    List<Order> findAllOrdersWithActiveDiningTables();

    // Tìm Order theo diningTableId nhưng chỉ lấy bàn chưa bị xóa mềm
    @Query("SELECT o FROM Order o WHERE o.diningTable.diningTableId = :diningTableId AND o.diningTable.isDeleted = false")
    Optional<Order> findByDiningTable_DiningTableId(@Param("diningTableId") int diningTableId);

    // Tìm Order đang hoạt động của bàn ăn chưa bị xóa mềm
    @Query("SELECT o FROM Order o WHERE o.diningTable.diningTableId = :diningTableId AND o.status IN ('PENDING') AND o.diningTable.isDeleted = false")
    Optional<Order> findActiveOrderByDiningTableId(@Param("diningTableId") int diningTableId);

    // Tìm tất cả Order của nhà hàng nhưng chỉ lấy bàn chưa bị xóa mềm
    @Query("SELECT o FROM Order o WHERE o.diningTable.restaurant.restaurantId = :restaurantId AND o.diningTable.isDeleted = false")
    List<Order> findByDiningTable_Restaurant_RestaurantId(@Param("restaurantId") int restaurantId);


    //  Tổng doanh thu **tất cả nhà hàng** theo ngày (year, month, day)
    @Query("""
        SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o
        WHERE o.status = 'COMPLETED'
        AND FUNCTION('YEAR', o.updatedAt) = :year
        AND FUNCTION('MONTH', o.updatedAt) = :month
        AND FUNCTION('DAY', o.updatedAt) = :day
    """)
    BigDecimal getTotalRevenueByDay(@Param("year") int year, @Param("month") int month, @Param("day") int day);

    //  Tổng doanh thu **từng nhà hàng** theo ngày (year, month, day)
    @Query("""
        SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o
        WHERE o.status = 'COMPLETED'
        AND o.diningTable.restaurant.restaurantId = :restaurantId
        AND FUNCTION('YEAR', o.updatedAt) = :year
        AND FUNCTION('MONTH', o.updatedAt) = :month
        AND FUNCTION('DAY', o.updatedAt) = :day
    """)
    BigDecimal getRestaurantRevenueByDay(@Param("restaurantId") int restaurantId, @Param("year") int year, @Param("month") int month, @Param("day") int day);


    //  Doanh thu của **tất cả nhà hàng** theo tháng
    @Query("""
        SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o
        WHERE o.status = 'COMPLETED'
        AND FUNCTION('YEAR', o.updatedAt) = :year
        AND FUNCTION('MONTH', o.updatedAt) = :month
    """)
    BigDecimal getTotalRevenueByMonth(@Param("year") int year, @Param("month") int month);

    //  Doanh thu của **từng nhà hàng** theo tháng
    @Query("""
        SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o
        WHERE o.status = 'COMPLETED'
        AND o.diningTable.restaurant.restaurantId = :restaurantId
        AND FUNCTION('YEAR', o.updatedAt) = :year
        AND FUNCTION('MONTH', o.updatedAt) = :month
    """)
    BigDecimal getRestaurantRevenueByMonth(@Param("restaurantId") int restaurantId, @Param("year") int year, @Param("month") int month);


    // Doanh thu của **tất cả nhà hàng** theo năm
    @Query("""
        SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o
        WHERE o.status = 'COMPLETED'
        AND FUNCTION('YEAR', o.updatedAt) = :year
    """)
    BigDecimal getTotalRevenueByYear(@Param("year") int year);

    //  Doanh thu của **từng nhà hàng** theo năm
    @Query("""
        SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o
        WHERE o.status = 'COMPLETED'
        AND o.diningTable.restaurant.restaurantId = :restaurantId
        AND FUNCTION('YEAR', o.updatedAt) = :year
    """)
    BigDecimal getRestaurantRevenueByYear(@Param("restaurantId") int restaurantId, @Param("year") int year);



    //  Tổng doanh thu **tất cả nhà hàng** từ ngày bắt đầu đến ngày kết thúc
    @Query("""
        SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o
        WHERE o.status = 'COMPLETED'
        AND FUNCTION('DATE', o.updatedAt) BETWEEN :startDate AND :endDate
    """)
    BigDecimal getTotalRevenueBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //  Tổng doanh thu **từng nhà hàng** từ ngày bắt đầu đến ngày kết thúc
    @Query("""
        SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o
        WHERE o.status = 'COMPLETED'
        AND o.diningTable.restaurant.restaurantId = :restaurantId
        AND FUNCTION('DATE', o.updatedAt) BETWEEN :startDate AND :endDate
    """)
    BigDecimal getRestaurantRevenueBetweenDates(@Param("restaurantId") int restaurantId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

//    // Thêm phương thức: Tìm Order theo diningTableId (không giới hạn trạng thái)
//    Optional<Order> findByDiningTable_DiningTableId(int diningTableId);
//
//    @Query("SELECT o FROM Order o WHERE o.diningTable.diningTableId = :diningTableId AND o.status IN ('PENDING')")
//    Optional<Order> findActiveOrderByDiningTableId(@Param("diningTableId") int diningTableId);

//    List<Order> findByDiningTable_Restaurant_RestaurantId(int restaurantId);

}
