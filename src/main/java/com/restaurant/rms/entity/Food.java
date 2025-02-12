package com.restaurant.rms.entity;

import com.restaurant.rms.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private int food_id;
    @Column(name = "name",unique = true)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private double price;
    @Column(name = "image_url")
    private String image_url;
    @Column(name = "category_id")
    private int category_id;
    @Enumerated(EnumType.STRING)
    Status status;

}
