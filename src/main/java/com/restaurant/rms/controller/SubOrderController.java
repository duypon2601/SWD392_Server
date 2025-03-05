package com.restaurant.rms.controller;


import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
import com.restaurant.rms.service.orderService.SubOrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/sub_order")
@SecurityRequirement(name = "api")
public class SubOrderController {
    private final SubOrderService subOrderService;

    // Tạo một SubOrder mới
    @PostMapping("/create")
    public ResponseEntity<String> createSubOrder(@RequestBody SubOrderDTO subOrderDTO) {
        subOrderService.createSubOrder(subOrderDTO);
        return ResponseEntity.ok("SubOrder has been created.");
    }

    // Hoàn tất SubOrder (gộp vào Order chính)
    @PutMapping("/{subOrderId}/complete")
    public ResponseEntity<String> completeSubOrder(@PathVariable int subOrderId) {
        subOrderService.completeSubOrder(subOrderId);
        return ResponseEntity.ok("SubOrder has been merged into the main Order.");
    }
}
