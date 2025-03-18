package com.restaurant.rms.dto.request;

import com.restaurant.rms.enums.DiningTableStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDiningTableDTO {
    private int id;
    private DiningTableStatus status;
    private int restaurantId;
}
