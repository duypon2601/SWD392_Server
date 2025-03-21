package com.restaurant.rms.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private int restaurantId;
    private String name;
    private String location;
    private boolean isDeleted;
}
