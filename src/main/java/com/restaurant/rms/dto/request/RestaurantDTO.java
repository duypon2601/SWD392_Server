package com.restaurant.rms.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    private int restaurantId;

    private String name;

    private String location;

//    private List<UserDTO> users;
}
