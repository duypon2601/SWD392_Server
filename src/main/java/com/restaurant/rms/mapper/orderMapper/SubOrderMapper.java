//package com.restaurant.rms.mapper.orderMapper;
//
//import com.restaurant.rms.dto.request.orderDTO.SubOrderDTO;
//import com.restaurant.rms.dto.request.orderDTO.SubOrderItemDTO;
//import com.restaurant.rms.entity.SubOrder;
//import com.restaurant.rms.entity.SubOrderItem;
//
//import java.util.stream.Collectors;
//
//public class SubOrderMapper {
//    public static SubOrderDTO toDTO(SubOrder subOrder) {
//        return SubOrderDTO.builder()
//                .id(subOrder.getId())
//                .orderId(subOrder.getOrder().getId())
//                .status(subOrder.getStatus().name())
//                .totalPrice(subOrder.getTotalPrice())
//                .subOrderItems(subOrder.getSubOrderItems().stream().map(SubOrderMapper::toDTO).collect(Collectors.toList()))
//                .build();
//    }
//
//    public static SubOrderItemDTO toDTO(SubOrderItem subOrderItem) {
//        return SubOrderItemDTO.builder()
//                .id(subOrderItem.getId())
//                .menuItemId(subOrderItem.getMenuItem().getMenu_item_id())
//                .quantity(subOrderItem.getQuantity())
//                .price(subOrderItem.getPrice())
//                .build();
//    }
//}
