package com.restaurant.rms.entity;

import com.restaurant.rms.enums.DiningTableStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dining_table")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "qr_code", unique = true, nullable = false, length = 255)
    private String qrCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DiningTableStatus status = DiningTableStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
