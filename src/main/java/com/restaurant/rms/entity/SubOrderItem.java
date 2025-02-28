package com.restaurant.rms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sub_order_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_order_item_id")
    private int subOrderItemId;

    @ManyToOne
    @JoinColumn(name = "sub_order_id", nullable = false)
    private SubOrder subOrder;

    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private RestaurantMenuItem menuItem;

    private int quantity;
    private BigDecimal price;
}
