//package com.restaurant.rms.controller;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class NotificationController {
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    public NotificationController(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    // Gửi thông báo đến tất cả client SUBSCRIBE "/topic/menuUpdates"
//    public void sendMenuUpdate(String message) {
//        System.out.println("📢 Gửi thông báo: " + message);
//        messagingTemplate.convertAndSend("/topic/menuUpdates", message);
//
//    }
//}

