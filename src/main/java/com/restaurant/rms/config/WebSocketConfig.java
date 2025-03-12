//package com.restaurant.rms.config;
//
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws")  // Endpoint WebSocket
//                .setAllowedOrigins("*");
//                 // Hỗ trợ SockJS cho trình duyệt cũ
//    }
//
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/topic"); // Client sẽ SUBSCRIBE vào "/topic/..."
//        registry.setApplicationDestinationPrefixes("/app"); // Client sẽ SEND đến "/app/..."
//    }
//}

