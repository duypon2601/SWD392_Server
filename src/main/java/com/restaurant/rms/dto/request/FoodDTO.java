package com.restaurant.rms.dto.request;

import com.restaurant.rms.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private int food_id;
    private String name;
    private String description;
    private double price;
    private String image_url;
    private int category_id;
    private Status status;
}
