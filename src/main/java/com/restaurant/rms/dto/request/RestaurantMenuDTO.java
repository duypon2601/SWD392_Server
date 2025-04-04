package com.restaurant.rms.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantMenuDTO {
    private int id;
    private int restaurantId;
//    private String name;
//    private String description;
    private boolean isActive;
    private List<RestaurantMenuItemDTO> menuItems;
}

