package com.restaurant.rms.service.userService;



import com.restaurant.rms.util.error.IdInvalidException;
//import com.restaurant.rms.dto.request.TopicDTO;
import com.restaurant.rms.dto.request.UserDTO;
import com.restaurant.rms.dto.response.ResCreateUserDTO;
import com.restaurant.rms.dto.response.ResUpdateUserDTO;
import com.restaurant.rms.dto.response.ResUserDTO;
import com.restaurant.rms.entity.User;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO) throws IdInvalidException;

    UserDTO getUserById ( Integer user_id) throws IdInvalidException;

    List<ResUserDTO> getUserAll();

    UserDTO updateUser(UserDTO updateUser, Integer topic_id);

    void deleteUser (Integer user_id) throws IdInvalidException;

    UserDTO handleGetUserByUsername(String username);

    boolean isUsernameExist (String username);

    List<UserDTO> findUserByRestaurantId(int restaurant_id) throws IdInvalidException;

    ResCreateUserDTO convertToResCreateUserDTO(UserDTO user);

    ResUpdateUserDTO convertToResUpdateUserDTO(UserDTO user);

    ResUserDTO convertToResUserDTO(UserDTO user);
}
