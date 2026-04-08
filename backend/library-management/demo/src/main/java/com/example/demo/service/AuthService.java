package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import java.util.ArrayList;

@Service

public class AuthService {

    @Autowired
    UserRepository userRepository;

    public User signup(User user){

        if(userRepository.findByEmail(user.getEmail())!=null){

            throw new RuntimeException("User already exists");

        }

        if(user.getRole()==null){

            user.setRole("USER");

        }

        user.setBorrowedBooks(new ArrayList<>());

        return userRepository.save(user);

    }

    public User login(String email,String password){

        User user=
        userRepository.findByEmailAndPassword(email,password);

        if(user==null){

            throw new RuntimeException("Invalid credentials");

        }

        return user;

    }

}