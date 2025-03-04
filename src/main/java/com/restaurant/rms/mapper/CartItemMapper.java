//package com.restaurant.rms.mapper;
//
//import com.restaurant.rms.dto.request.CartItemDTO;
//import com.restaurant.rms.entity.CartItem;
//import com.restaurant.rms.entity.RestaurantMenuItem;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CartItemMapper {
//
//    public static CartItemDTO toDTO(CartItem cartItem) {
//        return CartItemDTO.builder()
//                .foodId(cartItem.getFood_id())
//                .menuItemId(cartItem.getMenuItem().getMenu_item_id()) // Chỉ lấy ID
//                .name(cartItem.getName())
//                .quantity(cartItem.getQuantity())
//                .price(cartItem.getPrice())
//                .build();
//    }
//
//    public CartItem toEntity(CartItemDTO cartItemDTO, RestaurantMenuItem menuItem) {
//        return CartItem.builder()
//                .food_id(cartItemDTO.getFoodId())
//                .menuItem(menuItem) // Chuyển đổi từ ID sang Entity
//                .name(cartItemDTO.getName())
//                .quantity(cartItemDTO.getQuantity())
//                .price(cartItemDTO.getPrice())
//                .build();
//    }
//}
