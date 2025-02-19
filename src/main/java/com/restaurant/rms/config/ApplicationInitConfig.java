package com.restaurant.rms.config;

import com.restaurant.rms.dto.request.UserDTO;
import com.restaurant.rms.entity.Restaurant;
import com.restaurant.rms.entity.User;
import com.restaurant.rms.enums.Role;
import com.restaurant.rms.mapper.UserMapper;
import com.restaurant.rms.repository.RestaurantRepository;
import com.restaurant.rms.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder;

    public ApplicationInitConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    ApplicationRunner applicationRunner(
            UserRepository userRepository,
            RestaurantRepository restaurantRepository
    ) {
        return args -> {
            // 1. Tạo restaurant mặc định
            Restaurant defaultRestaurant;
            if (!restaurantRepository.existsByName("Admin Restaurant")) {
                defaultRestaurant = new Restaurant();
                defaultRestaurant.setName("Admin Restaurant");
                defaultRestaurant.setLocation("Admin Location");
                defaultRestaurant = restaurantRepository.save(defaultRestaurant);
            } else {
                defaultRestaurant = restaurantRepository.findByName("Admin Restaurant")
                        .orElseThrow();
            }

            // 2. Tạo user admin
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setName("Administrator");
                admin.setEmail("admin@example.com");
                admin.setRole(Role.ADMIN);
                admin.setRestaurant(defaultRestaurant);
                admin.setRestaurant_name(defaultRestaurant.getName());

                userRepository.save(admin);
            }
        };
}
}
