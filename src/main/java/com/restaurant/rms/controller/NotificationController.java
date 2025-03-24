package com.restaurant.rms.controller;

import com.restaurant.rms.dto.request.NotificationRequestDTO;
import com.restaurant.rms.dto.response.NotificationResponseDTO;
import com.restaurant.rms.service.notificationService.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
@SecurityRequirement(name = "api")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<NotificationResponseDTO> sendNotification(@RequestBody NotificationRequestDTO requestDTO) {
        NotificationResponseDTO responseDTO = notificationService.sendNotification(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}