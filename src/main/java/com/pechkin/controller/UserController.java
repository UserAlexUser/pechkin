package com.pechkin.controller;

import com.pechkin.model.User;
import com.pechkin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable("id") Long id) {
        log.info("retrieving user");
        return this.userService.findById(id);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        log.info("retrieving all users");
        return ResponseEntity.ok(userService.findAll());
    }
}
