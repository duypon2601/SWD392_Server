//package com.restaurant.rms.mapper.orderMapper;
//
//import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
//import com.restaurant.rms.entity.Order;
//import com.restaurant.rms.entity.SubOrder;
//import com.restaurant.rms.enums.OrderStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.stream.Collectors;
//
//@Component
//public class SubOrderMapper {
//
//    @Autowired
//    private SubOrderItemMapper subOrderItemMapper;
//
//    public SubOrderDTO toDTO(SubOrder subOrder) {
//        if (subOrder == null) {
//            return null;
//        }
//        return SubOrderDTO.builder()
//                .id(subOrder.getSubOrderId())
//                .orderId(subOrder.getOrder().getOrderId())
//                .status(subOrder.getStatus().name())
//                .totalPrice(subOrder.getTotalPrice())
//                .subOrderItems(subOrder.getSubOrderItems() != null ?
//                        subOrder.getSubOrderItems().stream()
//                                .map(subOrderItemMapper::toDTO)
//                                .collect(Collectors.toList())
//                        : new ArrayList<>())
//                .build();
//    }
//
//    public SubOrder toEntity(SubOrderDTO subOrderDTO, Order order) {
//        if (subOrderDTO == null) {
//            return null;
//        }
//        SubOrder subOrder = SubOrder.builder()
//                .subOrderId(subOrderDTO.getId())
//                .order(order)
//                .status(OrderStatus.valueOf(subOrderDTO.getStatus()))
//                .totalPrice(subOrderDTO.getTotalPrice())
//                .subOrderItems(new ArrayList<>()) // Khởi tạo danh sách rỗng trước
//                .build();
//
//        if (subOrderDTO.getSubOrderItems() != null) {
//            subOrder.setSubOrderItems(subOrderDTO.getSubOrderItems().stream()
//                    .map(subOrderItemDTO -> subOrderItemMapper.toEntity(subOrderItemDTO, subOrder, null)) // Truyền thêm subOrder vào
//                    .collect(Collectors.toList()));
//        }
//
//        return subOrder;
//    }
//}
