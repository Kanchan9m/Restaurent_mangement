package com.example.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Restaurants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String email;

    private String state;

    private String city;
    private String pincode;

    @Column(nullable = false)
    private Boolean approved = false;

    @OneToMany(mappedBy = "restaurant")
    private List<UserRestaurant> staff = new ArrayList<>();


}
