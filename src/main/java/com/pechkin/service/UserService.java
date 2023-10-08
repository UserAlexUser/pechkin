package com.pechkin.service;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.pechkin.model.User;
import com.pechkin.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }
        userRepo.save(user);

        return true;
    }

    public Optional<ArrayList<User>> findAll() {
        return Optional.of((ArrayList<User>) userRepo.findAll());
    }
}