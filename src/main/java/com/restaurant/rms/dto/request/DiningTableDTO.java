package com.restaurant.rms.dto.request;

import com.restaurant.rms.enums.DiningTableStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiningTableDTO {
    private int id;
    private String qrCode;
    private DiningTableStatus status;
    private int restaurantId;
}