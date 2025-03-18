package com.restaurant.rms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurant_menu_item")
public class RestaurantMenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_menu_item_id")
    private int restaurantMenuItemId;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "restaurant_menu_id", nullable = false)
    private RestaurantMenu restaurantMenu;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

}
//    @Column(name = "stock_quantity", nullable = false)
//    private int stockQuantity;
//
//    @Column(name = "min_stock_threshold", nullable = false)
//    private int minStockThreshold;