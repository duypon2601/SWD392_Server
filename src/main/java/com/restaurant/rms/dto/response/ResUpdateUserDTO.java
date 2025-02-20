package com.restaurant.rms.dto.response;

import com.restaurant.rms.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResUpdateUserDTO {
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

    private int restaurant_id;
}
