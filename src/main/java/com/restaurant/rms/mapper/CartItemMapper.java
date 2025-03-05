package com.restaurant.rms.mapper;

import com.restaurant.rms.dto.request.CartItemDTO;
import com.restaurant.rms.entity.RestaurantMenuItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public static CartItemDTO toDTO(RestaurantMenuItem menuItem, int quantity) {
        return CartItemDTO.builder()
                .menuItemId(menuItem.getRestaurantMenuItemId())  // Lấy ID món ăn trong menu
                .name(menuItem.getFood().getName())  // Lấy tên món từ Food Entity
                .quantity(quantity)
                .price(menuItem.getPrice())  // Lấy giá món ăn
                .build();
    }



//    public CartItem toEntity(CartItemDTO cartItemDTO, RestaurantMenuItem menuItem) {
//        return CartItem.builder()
//                .food_id(cartItemDTO.getFoodId())
//                .menuItem(menuItem) // Chuyển đổi từ ID sang Entity
//                .name(cartItemDTO.getName())
//                .quantity(cartItemDTO.getQuantity())
//                .price(cartItemDTO.getPrice())
//                .build();
//    }
}
