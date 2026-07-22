package com.example.project.controller;

import com.example.project.dto.RegisterRequest;
import com.example.project.model.RoleType;
import com.example.project.model.Role;
import com.example.project.model.User;
import com.example.project.repositories.RoleRepository;
import com.example.project.repositories.UserRepository;
import com.example.project.security.request.LoginRequest;
import com.example.project.security.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/rms")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
//        Authentication authentication;
//    }

    @PostMapping("/register")
    public ResponseEntity<?> authenticateUser(@RequestBody RegisterRequest registerRequest){

        if(userRepository.existsByEmail(registerRequest.getEmail())){
            return ResponseEntity.badRequest().body(new MessageResponse("Email already exists!"));
        }

        if(userRepository.existsByUserName((registerRequest.getUsername()))){
            return ResponseEntity.badRequest().body(new MessageResponse("Username already exists!"));
        }

        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Role role = roleRepository
                .findByRoleName(registerRequest.getRole())
                .orElseThrow(() ->
                        new RuntimeException("Role Not Found"));

        user.setRole(role);

        userRepository.save(user);


        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Registered successfully"));

    }
}
