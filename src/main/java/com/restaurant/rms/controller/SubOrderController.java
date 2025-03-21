package com.restaurant.rms.controller;


import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
import com.restaurant.rms.service.orderService.SubOrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/sub_order")
@SecurityRequirement(name = "api")
public class SubOrderController {
    private final SubOrderService subOrderService;

    // Tạo một SubOrder mới
//    @PostMapping("/create")
//    public ResponseEntity<String> createSubOrder(@RequestBody SubOrderDTO subOrderDTO) {
//        subOrderService.createSubOrder(subOrderDTO);
//        return ResponseEntity.ok("SubOrder has been created.");
//    }

    // Hoàn tất SubOrder (gộp vào Order chính)
    @PutMapping("/{subOrderId}/complete")
    public ResponseEntity<String> completeSubOrder(@PathVariable int subOrderId) {
        subOrderService.confirmSubOrder(subOrderId);
        return ResponseEntity.ok("SubOrder has been merged into the main Order.");
    }

    // Thêm API: Lấy thông tin SubOrder theo ID
    @GetMapping("/{subOrderId}")
    public ResponseEntity<SubOrderDTO> getSubOrder(@PathVariable int subOrderId) {
        SubOrderDTO subOrderDTO = subOrderService.getSubOrderById(subOrderId);
        return ResponseEntity.ok(subOrderDTO);
    }

    // Thêm API: Lấy danh sách SubOrder theo DiningTable
    @GetMapping("/dining-table/{diningTableId}")
    public ResponseEntity<List<SubOrderDTO>> getSubOrderByDiningTable(@PathVariable int diningTableId) {
        List<SubOrderDTO> subOrders = subOrderService.getSubOrdersByDiningTable(diningTableId);
        return ResponseEntity.ok(subOrders);
    }

    // Thêm API: Lấy danh sách SubOrder theo Order ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<SubOrderDTO>> getSubOrderByOrderId(@PathVariable int orderId) {
        List<SubOrderDTO> subOrders = subOrderService.getSubOrdersByOrderId(orderId);
        return ResponseEntity.ok(subOrders);
    }

    // Thêm API: Xóa SubOrder
    @DeleteMapping("/{subOrderId}")
    public ResponseEntity<String> deleteSubOrder(@PathVariable int subOrderId) {
        subOrderService.deleteSubOrder(subOrderId);
        return ResponseEntity.ok("SubOrder has been deleted.");
    }
}
