package com.restaurant.rms.service.userService;



import com.restaurant.rms.dto.request.RestaurantDTO;
import com.restaurant.rms.entity.Restaurant;
import com.restaurant.rms.repository.RestaurantRepository;
import com.restaurant.rms.service.retaurantService.RestaurantService;
import com.restaurant.rms.util.error.IdInvalidException;
import com.restaurant.rms.dto.request.UserDTO;
import com.restaurant.rms.dto.response.ResCreateUserDTO;
import com.restaurant.rms.dto.response.ResUpdateUserDTO;
import com.restaurant.rms.dto.response.ResUserDTO;
import com.restaurant.rms.entity.User;
//import com.math.mathcha.mapper.TopicMapper;
import com.restaurant.rms.mapper.UserMapper;
import com.restaurant.rms.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private RestaurantRepository restaurantRepository;
    private RestaurantService restaurantService;

    @Override
    public UserDTO createUser(UserDTO userDTO) throws IdInvalidException {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IdInvalidException("Username " + userDTO.getUsername() + " đã tồn tại, vui lòng sử dụng email khác.");
        }
        Restaurant restaurant = restaurantRepository.findById(userDTO.getRestaurant_id())
                .orElseThrow(() -> new IdInvalidException("Restaurant ID không tồn tại"));

        userDTO.setRestaurant_name(restaurant.getName());

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = UserMapper.mapToUser(userDTO);
        user.setRestaurant(restaurant);
        user.setRole(userDTO.getRole());
        User savedUser= userRepository.save(user);
        return UserMapper.mapToUserDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Integer user_id) throws IdInvalidException {
        Optional<User> user = userRepository.findById(user_id);
        if (user.isPresent()) {
            return UserMapper.mapToUserDTO(user.get());
        }else{
            throw new IdInvalidException("User với id = " + user_id + " không tồn tại");
        }
    }

    @Override
    public List<ResUserDTO> getUserAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::mapToUserDTO)
                .map(this::convertToResUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UserDTO updateUser, Integer user_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(()-> new RuntimeException("User "+user_id+" not found"));

        Restaurant restaurant = restaurantRepository.findById(updateUser.getRestaurant_id())
                .orElseThrow(() -> new RuntimeException("Restaurant ID " + updateUser.getRestaurant_id() + " không tồn tại"));
        user.setName(updateUser.getName());
        user.setEmail(updateUser.getEmail());
        user.setUsername(updateUser.getUsername());
        user.setRole(updateUser.getRole());

        user.setRestaurant(restaurant);
        user.setRestaurant_name(restaurant.getName());
        User updateUserObj = userRepository.save(user);
        return UserMapper.mapToUserDTO(updateUserObj);
    }
//    xóa luôn
//    @Override
//    public void deleteUser(Integer user_id) throws IdInvalidException {
//        User user = userRepository.findById(user_id)
//                .orElseThrow(() -> new IdInvalidException("User với id = " + user_id + " không tồn tại"));
//        userRepository.deleteById(user_id);
//    }
    @Override
    public void deleteUser(Integer user_id) throws IdInvalidException {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new IdInvalidException("User với id = " + user_id + " không tồn tại hoặc đã bị xóa"));
        user.setDeleted(true); // Xóa mềm
        userRepository.save(user);
    }

    @Override
    public UserDTO handleGetUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Not exits"+username));

        return UserMapper.mapToUserDTO(user);
    }

    public boolean isUsernameExist(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public List<UserDTO> findUserByRestaurantId(int restaurant_id) throws IdInvalidException {
        List<User> users = userRepository.findUserByRestaurantId(restaurant_id);
        RestaurantDTO restaurantDTO = restaurantService.getRestaurantById(restaurant_id);
        if (restaurantDTO == null) {
            throw new IdInvalidException("Trong topic id = " + restaurant_id + " hiện không có lesson");
        }
        return users.stream().map(
                (user) -> UserMapper.mapToUserDTO(user)).collect(Collectors.toList()
        );
    }

    @Override
    public ResCreateUserDTO convertToResCreateUserDTO(UserDTO user) {
        ResCreateUserDTO res = new ResCreateUserDTO();
        res.setUser_id(user.getUser_id());
        res.setName(user.getName());
        res.setRestaurant_name(user.getRestaurant_name());
        res.setEmail(user.getEmail());
        res.setUsername(user.getUsername());
        res.setRole(user.getRole());
        res.setRestaurant_id(user.getRestaurant_id());
        return res;
    }
    @Override
    public ResUpdateUserDTO convertToResUpdateUserDTO(UserDTO user) {
        ResUpdateUserDTO res = new ResUpdateUserDTO();
        res.setUser_id(user.getUser_id());
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setRestaurant_name(user.getRestaurant_name());
        res.setUsername(user.getUsername());
        res.setRole(user.getRole());
        res.setRestaurant_id(user.getRestaurant_id());
        return res;
    }
    @Override
    public ResUserDTO convertToResUserDTO(UserDTO user) {
        ResUserDTO res = new ResUserDTO();
        res.setUser_id(user.getUser_id());
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setRestaurant_name(user.getRestaurant_name());
        res.setUsername(user.getUsername());
        res.setRole(user.getRole());
        res.setRestaurant_id(user.getRestaurant_id());
//        res.setDelete(user.getDelete() != null ? user.getDelete() : false);
        return res;
    }

    @Override
    public List<UserDTO> getDeletedUsers() {
        List<User> deletedUsers = userRepository.findAllByIsDeletedTrue();
        return deletedUsers.stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void restoreUser(Integer userId) throws IdInvalidException {
        User user = userRepository.findByIdAndIsDeletedTrue(userId)
                .orElseThrow(() -> new IdInvalidException("User với id = " + userId + " không tồn tại hoặc chưa bị xóa mềm"));
        user.setDeleted(false); // Phục hồi
        userRepository.save(user);
    }
}
