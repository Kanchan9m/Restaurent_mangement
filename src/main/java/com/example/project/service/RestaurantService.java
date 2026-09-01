package com.example.project.service;

import com.example.project.dto.request.RestaurantRequest;
import com.example.project.dto.response.RestaurantResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RestaurantService {
    RestaurantResponse createRestaurant(RestaurantRequest request);

    RestaurantResponse updateRestaurant(Long restaurantId, RestaurantRequest request);

    RestaurantResponse getRestaurantById(Long restaurantId);

    List<RestaurantResponse> getAllRestaurants();

    RestaurantResponse approveRestaurant(Long restaurantId);

    void deleteRestaurant(Long restaurantId);

    void assignOwner(Long restaurantId, Long userId);
}
