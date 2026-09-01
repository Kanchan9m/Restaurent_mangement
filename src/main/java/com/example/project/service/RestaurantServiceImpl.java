package com.example.project.service;

import com.example.project.dto.request.RestaurantRequest;
import com.example.project.dto.response.RestaurantResponse;
import com.example.project.exception.APIException;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.model.*;
import com.example.project.repositories.RestaurantRepository;
import com.example.project.repositories.RoleRepository;
import com.example.project.repositories.UserRepository;
import com.example.project.repositories.UserRestaurantRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRestaurantRepository userRestaurantRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public RestaurantResponse createRestaurant(RestaurantRequest request) {
        if (restaurantRepository.existsByEmail(request.getEmail())) {
            throw new APIException("Restaurant already exists ");
        }

        if (restaurantRepository.existsByRestaurantName(request.getRestaurantName())) {
            throw new APIException("Restaurant already exists " + request.getRestaurantName());
        }

        Restaurants restaurant = new Restaurants();

        restaurant.setRestaurantName(request.getRestaurantName());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhone(request.getPhone());
        restaurant.setEmail(request.getEmail());
        restaurant.setState(request.getState());
        restaurant.setCity(request.getCity());
        restaurant.setPincode(request.getPincode());

        restaurant.setApproved(false);

//        restaurant.setRating(BigDecimal.ZERO);

        Restaurants savedRestaurant = restaurantRepository.save(restaurant);

        return modelMapper.map(savedRestaurant, RestaurantResponse.class);

    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {

        return restaurantRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, RestaurantResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantResponse updateRestaurant(Long restaurantId, RestaurantRequest request) {
        Restaurants restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        restaurantRepository.findByEmail(request.getEmail())
                .ifPresent(existingRestaurant -> {
                    if (!existingRestaurant.getId().equals(restaurantId)) {
                        throw new APIException("Restaurant email already exists");
                    }
                });
        restaurant.setRestaurantName(request.getRestaurantName());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhone(request.getPhone());
        restaurant.setEmail(request.getEmail());
        restaurant.setState(request.getState());
        restaurant.setCity(request.getCity());
        restaurant.setPincode(request.getPincode());

        restaurant.setApproved(false);

//        restaurant.setRating(BigDecimal.ZERO);

        Restaurants updatedRestaurant = restaurantRepository.save(restaurant);

        return modelMapper.map(updatedRestaurant, RestaurantResponse.class);


    }

    @Override
    public RestaurantResponse getRestaurantById(Long restaurantId) {

        Restaurants restaurant = restaurantRepository.findById(restaurantId)
                        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        return modelMapper.map(restaurant, RestaurantResponse.class);

    }

    @Override
    public RestaurantResponse approveRestaurant(Long restaurantId) {

        Restaurants restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        if (Boolean.TRUE.equals(restaurant.getApproved())) {
            throw new APIException("Restaurant is already approved");
        }

        restaurant.setApproved(true);

        Restaurants savedRestaurant = restaurantRepository.save(restaurant);

        return modelMapper.map(savedRestaurant, RestaurantResponse.class);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {

        Restaurants restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        if (userRestaurantRepository.existsByRestaurant(restaurant)) {
            throw new APIException("Restaurant cannot be deleted because " + "users are assigned to it");
        }

        restaurantRepository.delete(restaurant);
    }

    @Override
    public void assignOwner(Long restaurantId, Long userId) {

        Restaurants restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (userRestaurantRepository.existsByUserAndRestaurant(user, restaurant)) {
            throw new APIException("User is already assigned to this restaurant");
        }

        Role ownerRole = roleRepository.findByRoleName(RoleType.ROLE_OWNER)
                .orElseThrow(() -> new ResourceNotFoundException("ROLE_OWNER not found"));

        UserRestaurant userRestaurant = new UserRestaurant();

        userRestaurant.setUser(user);
        userRestaurant.setRestaurant(restaurant);
        userRestaurant.setRole(ownerRole);

        userRestaurantRepository.save(userRestaurant);
    }

}
