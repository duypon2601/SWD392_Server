//package com.restaurant.rms.controller;
//
//
//import com.restaurant.rms.dto.request.orderDTO.OrderDTO;
//import com.restaurant.rms.dto.request.orderDTO.OrderItemDTO;
//import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
//import com.restaurant.rms.service.orderService.OrderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/orders")
//@RequiredArgsConstructor
//public class OrderController {
//    private final OrderService orderService;
//
//    // 📌 Tạo Order mới khi khách hàng đặt món lần đầu
//    @PostMapping("/create")
//    public ResponseEntity<OrderDTO> createOrder(
//            @RequestParam int tableId,
//            @RequestBody List<OrderItemDTO> orderItems) {
//        OrderDTO orderDTO = orderService.createOrder(tableId, orderItems);
//        return ResponseEntity.ok(orderDTO);
//    }
//
//    // 📌 Xử lý đặt món (Tạo Order mới hoặc SubOrder nếu đã có Order)
//    @PostMapping("/process")
//    public ResponseEntity<String> processOrder(
//            @RequestParam int tableId,
//            @RequestBody List<OrderItemDTO> orderItems) {
//        orderService.processOrder(tableId, orderItems);
//        return ResponseEntity.ok("Order processed successfully.");
//    }
//
////    // 📌 Lấy thông tin Order hiện tại của một bàn ăn
////    @GetMapping("/{tableId}")
////    public ResponseEntity<OrderDTO> getOrderByTable(@PathVariable int tableId) {
////        OrderDTO orderDTO = orderService.getOrderByTable(tableId);
////        return ResponseEntity.ok(orderDTO);
////    }
////
////    // 📌 Tạo một SubOrder khi khách gọi thêm món
////    @PostMapping("/{orderId}/suborder")
////    public ResponseEntity<SubOrderDTO> createSubOrder(
////            @PathVariable int orderId,
////            @RequestBody List<OrderItemDTO> orderItems) {
////        SubOrderDTO subOrderDTO = orderService.createSubOrder(orderId, orderItems);
////        return ResponseEntity.ok(subOrderDTO);
////    }
//
//    // 📌 Hoàn thành một SubOrder và cập nhật vào Order chính
//    @PutMapping("/suborder/{subOrderId}/complete")
//    public ResponseEntity<String> completeSubOrder(@PathVariable int subOrderId) {
//        orderService.completeSubOrder(subOrderId);
//        return ResponseEntity.ok("SubOrder completed successfully.");
//    }
//
//    // 📌 Lấy thông tin của một SubOrder
////    @GetMapping("/suborder/{subOrderId}")
////    public ResponseEntity<SubOrderDTO> getSubOrderById(@PathVariable int subOrderId) {
////        SubOrderDTO subOrderDTO = orderService.getSubOrderById(subOrderId);
////        return ResponseEntity.ok(subOrderDTO);
////    }
//}
