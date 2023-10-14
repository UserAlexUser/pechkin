package com.pechkin.service.impl;

import com.pechkin.exception.EmailAlreadyExistsException;
import com.pechkin.exception.UsernameAlreadyExistsException;
import com.pechkin.model.Role;
import com.pechkin.model.Status;
import com.pechkin.model.User;
import com.pechkin.repos.RoleRepo;
import com.pechkin.repos.UserRepo;
import com.pechkin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
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

        Role roleUser = roleRepo.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        log.info("user successfully registered");

        return userRepo.save(user);
    }
    @Override
    public List<User> findAll() {
        log.info("retrieving all users");
        return userRepo.findAll();
    }
    @Override
    public User findByUsername(String username) {
        log.info("found by username: {}", username);
        return userRepo.findByUsername(username);
    }
    @Override
    public User findById(Long id) {
        log.info("findById {}", id);
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        log.info("deleted user {}", id);
        userRepo.deleteById(id);
    }
}
