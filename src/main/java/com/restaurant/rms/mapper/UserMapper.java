package com.restaurant.rms.mapper;



import com.restaurant.rms.dto.request.UserDTO;
import com.restaurant.rms.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO mapToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(user.getUser_id());
        userDTO.setName(user.getName());
        userDTO.setRestaurant_name(user.getRestaurant_name());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setRestaurant_id(user.getRestaurant().getRestaurant_id());
        return userDTO;

    }
    public static User mapToUser(UserDTO userDTO){
        User user = new User();
        user.setUser_id(userDTO.getUser_id());
        user.setName(userDTO.getName());
        user.setRestaurant_name(userDTO.getRestaurant_name());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return user;

    }
}
