package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.dto.LoginRequest;

@RestController

@RequestMapping("/auth")

public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")

    public User signup(@RequestBody User user){

        return authService.signup(user);

    }

    @PostMapping("/login")

    public User login(@RequestBody LoginRequest request){

        return authService.login(

                request.getEmail(),
                request.getPassword()

        );

    }

}