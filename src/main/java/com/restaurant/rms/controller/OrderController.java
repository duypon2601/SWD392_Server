package com.restaurant.rms.controller;

import com.restaurant.rms.dto.request.orderDTO.OrderDTO;
import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
import com.restaurant.rms.service.orderService.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/order")
@SecurityRequirement(name = "api")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrderOrSubOrder(@RequestBody OrderDTO orderDTO) {
        log.info("📥 Nhận request tạo order: {}", orderDTO);

        boolean hasExistingOrder = orderService.hasExistingOrder(orderDTO.getDiningTableId());
        Object result = orderService.createOrderOrSubOrder(orderDTO);

        if (hasExistingOrder) {
            return ResponseEntity.status(HttpStatus.CREATED).body((SubOrderDTO) result);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body((OrderDTO) result);
        }
    }


    // Tạo Order hoặc SubOrder
//    @PostMapping("/create")
//    public ResponseEntity<OrderDTO> createOrderOrSubOrder(@RequestBody OrderDTO orderDTO) {
//        log.info("📥 Nhận request tạo order: {}", orderDTO);
//        return ResponseEntity.ok(orderService.createOrderOrSubOrder(orderDTO));
//    }

    // Hoàn tất Order (chuyển trạng thái COMPLETED)
    @PutMapping("/{orderId}/complete")
    public ResponseEntity<String> completeOrder(@PathVariable int orderId) {
        orderService.completeOrder(orderId);
        return ResponseEntity.ok("Order has been completed.");
    }

    @GetMapping("/{orderId}/receipt/completed")
    public ResponseEntity<OrderDTO> getReceiptByOrderId(@PathVariable Integer orderId) {
        OrderDTO orderDTO = orderService.getCompletedOrderReceipt(orderId);
        return ResponseEntity.ok(orderDTO);
    }

    // ✅ API: Doanh thu **tất cả nhà hàng** theo ngày
    @GetMapping("/revenue/day")
    public ResponseEntity<BigDecimal> getTotalRevenueByDay(@RequestParam int year, @RequestParam int month, @RequestParam int day) {
        return ResponseEntity.ok(orderService.getTotalRevenueByDay(year, month, day));
    }

    // ✅ API: Doanh thu **từng nhà hàng** theo ngày
    @GetMapping("/revenue/day/{restaurantId}")
    public ResponseEntity<BigDecimal> getRestaurantRevenueByDay(@PathVariable int restaurantId, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
        return ResponseEntity.ok(orderService.getRestaurantRevenueByDay(restaurantId, year, month, day));
    }
    // ✅ API: Doanh thu **tất cả nhà hàng** theo tháng
    @GetMapping("/revenue/month")
    public ResponseEntity<BigDecimal> getTotalRevenueByMonth(@RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(orderService.getTotalRevenueByMonth(year, month));
    }

    // ✅ API: Doanh thu **từng nhà hàng** theo tháng
    @GetMapping("/revenue/month/{restaurantId}")
    public ResponseEntity<BigDecimal> getRestaurantRevenueByMonth(@PathVariable int restaurantId, @RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(orderService.getRestaurantRevenueByMonth(restaurantId, year, month));
    }

    // ✅ API: Doanh thu **tất cả nhà hàng** theo năm
    @GetMapping("/revenue/year")
    public ResponseEntity<BigDecimal> getTotalRevenueByYear(@RequestParam int year) {
        return ResponseEntity.ok(orderService.getTotalRevenueByYear(year));
    }

    // ✅ API: Doanh thu **từng nhà hàng** theo năm
    @GetMapping("/revenue/year/{restaurantId}")
    public ResponseEntity<BigDecimal> getRestaurantRevenueByYear(@PathVariable int restaurantId, @RequestParam int year) {
        return ResponseEntity.ok(orderService.getRestaurantRevenueByYear(restaurantId, year));
    }
    // ✅ API: Doanh thu **tất cả nhà hàng** từ ngày bắt đầu đến ngày kết thúc
    @GetMapping("/revenue/daterange")
    public ResponseEntity<BigDecimal> getTotalRevenueBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(orderService.getTotalRevenueBetweenDates(startDate, endDate));
    }

    // ✅ API: Doanh thu **từng nhà hàng** từ ngày bắt đầu đến ngày kết thúc
    @GetMapping("/revenue/daterange/{restaurantId}")
    public ResponseEntity<BigDecimal> getRestaurantRevenueBetweenDates(
            @PathVariable int restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(orderService.getRestaurantRevenueBetweenDates(restaurantId, startDate, endDate));
    }

}
