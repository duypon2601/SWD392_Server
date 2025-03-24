package com.restaurant.rms.entity;

import com.restaurant.rms.enums.DiningTableStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dining_table")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Where(clause = "is_deleted = false")
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dining_table_Id")
    private int diningTableId;

    @Column(name = "qr_code", unique = true, nullable = false, length = 255)
    private String qrCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DiningTableStatus status = DiningTableStatus.AVAILABLE;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
