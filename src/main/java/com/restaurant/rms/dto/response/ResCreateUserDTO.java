package com.restaurant.rms.dto.response;

import com.restaurant.rms.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ResCreateUserDTO {
    private int user_id;

    private String name;

    private String restaurant_name;

    private String email;

//    private String address;
//
//    private String image;

    private String username;

//    private String password;

//    private Boolean delete;

    private Role role;


}
