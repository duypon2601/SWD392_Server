package com.restaurant.rms.entity;

import com.restaurant.rms.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "sub_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_order_id")
    private int subOrderId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToMany(mappedBy = "subOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubOrderItem> subOrderItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();
}

