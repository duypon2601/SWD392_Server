package com.restaurant.rms.mapper;


import com.restaurant.rms.dto.request.PaymentDTO;
import com.restaurant.rms.entity.Order;
import com.restaurant.rms.entity.Payment;


public class PaymentMapper {
    public static PaymentDTO mapToPaymentDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setOrderId(payment.getOrder().getOrderId());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setTransactionId(payment.getTransactionId());
        dto.setPaidAt(payment.getPaidAt());
        return dto;
    }

    public static Payment mapToPayment(PaymentDTO dto, Order order) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setOrder(order);
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPaymentStatus(dto.getPaymentStatus());
        payment.setTransactionId(dto.getTransactionId());
        payment.setPaidAt(dto.getPaidAt());
        return payment;
    }
}
