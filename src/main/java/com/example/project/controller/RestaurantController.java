package com.example.project.controller;

import com.example.project.dto.request.RestaurantRequest;
import com.example.project.dto.response.RestaurantResponse;
import com.example.project.security.UserDetailsImpl;
import com.example.project.service.AdminAuthorizationService;
import com.example.project.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rms")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private AdminAuthorizationService adminAuthorizationService;

    @PostMapping("/owner/restaurant")
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody RestaurantRequest request,
                                                               Authentication authentication) {

//        checkAdmin(authentication);
        RestaurantResponse response = restaurantService.createRestaurant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/owner/restaurant")
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants(Authentication authentication) {

//        checkAdmin(authentication);

        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/owner/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long restaurantId,
                                                                Authentication authentication) {

//        checkAdmin(authentication);

        return ResponseEntity.ok(restaurantService.getRestaurantById(restaurantId));
    }

    @PutMapping("/restaurant/{restaurantId}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable Long restaurantId,
                                                               @Valid @RequestBody RestaurantRequest request,
                                                               Authentication authentication) {
//        checkAdmin(authentication);
        return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantId, request));
    }

    @PatchMapping("/restaurant/{restaurantId}/approve")
    public ResponseEntity<RestaurantResponse> approveRestaurant(@PathVariable Long restaurantId,
                                                                Authentication authentication) {

        checkAdmin(authentication);
        return ResponseEntity.ok(restaurantService.approveRestaurant(restaurantId));
    }

    @DeleteMapping("/admin/restaurant/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long restaurantId,
                                                 Authentication authentication) {

        checkAdmin(authentication);

        restaurantService.deleteRestaurant(restaurantId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin/restaurant/{restaurantId}/owner/{userId}")
    public ResponseEntity<String> assignOwner(@PathVariable Long restaurantId,
                                              @PathVariable Long userId, Authentication authentication) {

        checkAdmin(authentication);

        restaurantService.assignOwner(restaurantId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Owner assigned successfully");
    }


    private void checkAdmin(Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        adminAuthorizationService.checkAdmin(userDetails.getId());
    }



}
