package com.restaurant.rms.dto.request;


import lombok.Data;

@Data
public class RechargeRequestDTO {
    String amount;
    int orderId;

}
