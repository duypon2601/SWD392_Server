package com.restaurant.rms.entity;

import com.restaurant.rms.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int user_id;
    @Column(name = "name")
    private String name;
    @Column(name = "restaurant_name")
    private String restaurant_name;
    @Column(name = "email",unique = true)
    private String email;
//    @Column(name = "address")
//    private String address;
//    @Column(name = "image")
//    private String image;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
//    @Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
//    private Boolean is_deleted ;
    @Enumerated(EnumType.STRING)
    Role role;

}