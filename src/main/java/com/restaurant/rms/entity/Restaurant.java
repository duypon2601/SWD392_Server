package com.restaurant.rms.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "retaurant")

public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private int restaurant_id;
    @Column(name = "manager_id")
    private int manager_id;
    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private String location;
//    @ManyToOne
//    @JoinColumn(name = "course_id", nullable = false)
//    @JsonIgnore
//    private Course course;
//
//    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    private List<Topic> topics = new ArrayList<Topic>();
//    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    private List<Quiz> quizs;

}
