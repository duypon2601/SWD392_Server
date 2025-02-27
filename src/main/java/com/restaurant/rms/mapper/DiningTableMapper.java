package com.restaurant.rms.mapper;

import com.restaurant.rms.dto.request.DiningTableDTO;
import com.restaurant.rms.entity.DiningTable;
import com.restaurant.rms.entity.Restaurant;

public class DiningTableMapper {
    public static DiningTableDTO toDTO(DiningTable diningTable) {
        return DiningTableDTO.builder()
                .id(diningTable.getId())
                .qrCode(diningTable.getQrCode())
                .status(diningTable.getStatus())
                .restaurantId(diningTable.getRestaurant().getRestaurant_id())
                .build();
    }

    public static DiningTable toEntity(DiningTableDTO dto) {
        DiningTable diningTable = new DiningTable();
        diningTable.setId(dto.getId());
        diningTable.setQrCode(dto.getQrCode());
        diningTable.setStatus(dto.getStatus());

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurant_id(dto.getRestaurantId());
        diningTable.setRestaurant(restaurant);

        return diningTable;
    }
}