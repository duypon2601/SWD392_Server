//package com.restaurant.rms.service.orderService;
//
//import com.restaurant.rms.dto.request.orderDTO.OrderDTO;
//import com.restaurant.rms.dto.request.orderDTO.OrderItemDTO;
//import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
//import com.restaurant.rms.entity.Order;
//import com.restaurant.rms.entity.OrderItem;
//
//import java.util.List;
//
//public interface OrderService {
//    OrderDTO createOrder(int tableId, List<OrderItemDTO> orderItemDTOs);
//    void processOrder(int diningTableId, List<OrderItemDTO> orderItemDTOs);
//    void completeSubOrder(int subOrderId);
//    Order getOrderByTable(int diningTableId);
//    SubOrderDTO getSubOrderById(int subOrderId);
//
//}
//
