package com.pechkin.service;

import com.pechkin.exception.EmailAlreadyExistsException;
import com.pechkin.exception.UsernameAlreadyExistsException;
import com.pechkin.model.User;
import com.pechkin.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;
    public User addUser(User user) {
        if(userRepo.existsByUsername(user.getUsername())) {
            log.info("username {} already exists.", user.getUsername());

            throw new UsernameAlreadyExistsException(
                    String.format("username %s already exists", user.getUsername()));
        }

        if(userRepo.existsByEmail(user.getEmail())) {
            log.info("email {} already exists.", user.getEmail());

            throw new EmailAlreadyExistsException(
                    String.format("email %s already exists", user.getEmail()));
        }

        return userRepo.save(user);
    }

    public List<User> findAll() {
        log.info("retrieving all users");
        return userRepo.findAll();
    }

    public Optional<User> findByUsername(String username) {
        log.info("retrieving user {}", username);
        return userRepo.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        log.info("retrieving user {}", id);
        return userRepo.findById(id);
    }
}
