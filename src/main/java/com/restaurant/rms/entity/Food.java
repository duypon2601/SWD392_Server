package com.restaurant.rms.entity;

import com.restaurant.rms.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Where(clause = "is_deleted = false") // Chỉ lấy các bản ghi chưa bị xóa mềm
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private int foodId;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String image_url;

    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false; // Thêm trường isDeleted, mặc định là false

    // Relationship
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<RestaurantMenuItem> menu_item;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;
}
