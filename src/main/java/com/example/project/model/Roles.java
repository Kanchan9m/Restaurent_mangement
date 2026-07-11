package com.example.project.model;

import jakarta.persistence.*;

@Entity
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role_id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    private RoleType rolename;

}
