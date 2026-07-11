package com.example.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private int phone;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;
}
