package com.restaurant.rms.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurant_menu_item")

public class RestaurantMenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id")
    private int menu_item_id;


    @Column(name ="price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock_quantity")
    private int stock_quantity;

    @Column(name = "min_stock_threshold")
    private int min_stock_threshold;


    //relationship
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;


}