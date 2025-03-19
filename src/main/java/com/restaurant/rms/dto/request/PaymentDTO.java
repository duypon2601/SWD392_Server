package com.restaurant.rms.dto.request;

import com.restaurant.rms.enums.PaymentMethod;
import com.restaurant.rms.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private int id;
    private int orderId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private LocalDateTime paidAt;
}
