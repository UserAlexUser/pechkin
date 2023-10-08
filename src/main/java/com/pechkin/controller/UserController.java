package com.pechkin.controller;

import com.pechkin.service.UserService;
import com.pechkin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/allusers")
    public ArrayList<User> getUsers() {
        Optional users = userService.findAll();
        return (ArrayList<User>) users.get();
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);

        return "userEdit";
    }

    @GetMapping("profile")
    public String getProfile(Model model, User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }
}
