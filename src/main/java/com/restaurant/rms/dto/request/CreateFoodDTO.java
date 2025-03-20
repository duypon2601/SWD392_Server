package com.restaurant.rms.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateFoodDTO {
    private String name;
    private String description;
    private String image_url;
    private int category_id;
}
