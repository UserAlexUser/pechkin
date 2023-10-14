package com.pechkin.service;

import com.pechkin.model.User;
import java.util.List;

public interface UserService {

    User addUser(User user);

    List<User> findAll();

    User findByUsername(String username);

    User findById(Long id);

    void delete(Long id);
}
