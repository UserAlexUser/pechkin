package com.pechkin.controller;

import com.pechkin.dto.*;
import com.pechkin.exception.UserAlreadyExistException;
import com.pechkin.model.User;
import com.pechkin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("user/profile")
    public ResponseEntity<User> updateProfile(@RequestBody UserUpdateDto updateUser,
                                              HttpServletRequest request) {
        return new ResponseEntity<>(userService.updateProfile(updateUser, request), HttpStatus.OK);
    }

    @PostMapping("user/password")
    public ResponseEntity<User> updateProfilePassword(@RequestBody UserPasswordDto updatePassword,
                                                      HttpServletRequest request) {
        return new ResponseEntity<>(userService.updateProfilePassword(updatePassword, request), HttpStatus.OK);
    }

    @DeleteMapping("user/delete")
    public ResponseEntity<?> deleteCurrentUser(HttpServletRequest request) {
            userService.deleteUser(request);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/auth/registration")
    public ResponseEntity<User> registration(@RequestBody UserRegisterDto registerUser)
            throws UserAlreadyExistException {
        return new ResponseEntity<>(userService.registerUser(registerUser), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserLoginDto userLogin) {
        return new ResponseEntity<>(userService.login(userLogin), HttpStatus.OK);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}