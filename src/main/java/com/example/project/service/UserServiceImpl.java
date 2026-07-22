package com.example.project.service;

import com.example.project.dto.RegisterRequest;
import com.example.project.repositories.RoleRepository;
import com.example.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImpl implements UserService{



    @Override
    public String register(RegisterRequest request) {

        return "";
    }
}
