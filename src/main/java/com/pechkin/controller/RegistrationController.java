package com.pechkin.controller;

import com.pechkin.model.User;
import com.pechkin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public List<User> addUser(User user) {
        return (List<User>) userService.addUser(user);
    }
}
