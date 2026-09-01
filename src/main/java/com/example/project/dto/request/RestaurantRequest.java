package com.example.project.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RestaurantRequest {

    private String restaurantName;

    private String address;

    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    private String state;
    private String city;
    private String pincode;

}
