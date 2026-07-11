package com.example.project.model;

import jakarta.persistence.Entity;

@Entity
public class Restaurents {

    private int restaurant_id;
    private String restaurant_name;
    private String address;
    private int phone;
    private Boolean approved;
    private int owner_id;

}
