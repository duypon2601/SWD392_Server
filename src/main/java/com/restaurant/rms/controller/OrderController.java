package com.restaurant.rms.controller;

import com.restaurant.rms.dto.request.orderDTO.OrderDTO;
import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
import com.restaurant.rms.service.orderService.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
}
