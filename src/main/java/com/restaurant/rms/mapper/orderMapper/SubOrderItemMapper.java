//package com.restaurant.rms.mapper.orderMapper;
//
//import com.restaurant.rms.dto.request.orderDTO.SubOrderItemDTO;
//import com.restaurant.rms.entity.RestaurantMenuItem;
//import com.restaurant.rms.entity.SubOrder;
//import com.restaurant.rms.entity.SubOrderItem;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SubOrderItemMapper {
//
//    public SubOrderItemDTO toDTO(SubOrderItem subOrderItem) {
//        if (subOrderItem == null) {
//            return null;
//        }
//        return SubOrderItemDTO.builder()
//                .id(subOrderItem.getSubOrderItemId())
//                .menuItemId(subOrderItem.getMenuItem().getMenu_item_id())
//                .quantity(subOrderItem.getQuantity())
//                .price(subOrderItem.getPrice())
//                .build();
//    }
//
//    public SubOrderItem toEntity(SubOrderItemDTO subOrderItemDTO, SubOrder subOrder, RestaurantMenuItem menuItem) {
//        if (subOrderItemDTO == null || subOrder == null || menuItem == null) {
//            return null;
//        }
//        return SubOrderItem.builder()
//                .subOrderItemId(subOrderItemDTO.getId())
//                .subOrder(subOrder)
//                .menuItem(menuItem)
//                .quantity(subOrderItemDTO.getQuantity())
//                .price(subOrderItemDTO.getPrice())
//                .build();
//    }
//}
