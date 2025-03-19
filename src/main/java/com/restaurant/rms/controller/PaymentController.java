package com.restaurant.rms.controller;


import com.restaurant.rms.dto.request.RechargeRequestDTO;
import com.restaurant.rms.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/payment")
@SecurityRequirement(name = "api")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("/create")
//    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<String> createPayment(@RequestBody RechargeRequestDTO paymentDTO) throws Exception {
        String savedPayment = paymentService.createUrl(paymentDTO);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }

    @PostMapping("/callback")
//    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<String> paymentCallback(@RequestBody Map<String, String> queryParams) {
        paymentService.handlePaymentCallback(queryParams);
        return new ResponseEntity<>("Payment processed", HttpStatus.OK);
    }
}