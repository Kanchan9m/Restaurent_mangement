package com.example.project.model;

import jakarta.persistence.*;

@Entity
public class Restaurents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurant_id;

    private String restaurant_name;
    private String address;
    private String phone;
    private Boolean approved;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

}
