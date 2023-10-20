package com.pechkin.service;

import com.pechkin.dto.*;
import com.pechkin.exception.UserAlreadyExistException;
import com.pechkin.exception.EmailAlreadyExistsException;
import com.pechkin.model.Role;
import com.pechkin.model.Status;
import com.pechkin.model.User;
import com.pechkin.repos.RoleRepo;
import com.pechkin.repos.UserRepo;
import com.pechkin.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;


    public UserService(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserRepo userRepo, RoleRepo roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRegisterDto registerUser) throws UserAlreadyExistException {
        User userFromDb = this.findByUsername(registerUser.getUsername());
        if (userFromDb != null) {
            throw new UserAlreadyExistException();
        }

        Role role = this.roleRepo.findByRoleName("ROLE_USER");
        User user = new User();
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setEmail(registerUser.getEmail());
        user.setFirstName(registerUser.getFirstName());
        user.setSecondName(registerUser.getSecondName());
        user.setUsername(registerUser.getUsername());
        user.setRoles(Collections.singletonList(role));
        user.setPassword(this.passwordEncoder.encode(registerUser.getPassword()));
        user.setStatus(Status.ACTIVE);
        return this.userRepo.save(user);
    }

    public AuthResponseDto login(@RequestBody UserLoginDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = this.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            return new AuthResponseDto(username, token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public List<User> findAll() {
        log.info("retrieving all users");
        return userRepo.findAll();
    }

    public User findByUsername(String username) {
        log.info("found by username: {}", username);
        return userRepo.findByUsername(username);
    }

    public User findById(Long id) {
        log.info("findById {}", id);
        return userRepo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        log.info("deleted user {}", id);
        userRepo.deleteById(id);
    }

    public User updateProfilePassword(UserPasswordDto passwordUser, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        User userFromDb = this.userRepo.findByUsername(jwtTokenProvider.getUsername(token));
        if (!StringUtils.isEmpty(passwordUser.getNewPassword())) {
            if (userFromDb.getPassword().equals(passwordEncoder.encode(passwordUser.getOldPassword()))) {
                userFromDb.setPassword(passwordEncoder.encode(passwordUser.getNewPassword()));
            }
        }
        return userRepo.save(userFromDb);
    }

    public User updateProfile(UserUpdateDto updateUser, HttpServletRequest request){
        String token = jwtTokenProvider.resolveToken(request);
        User userFromDb = this.userRepo.findByUsername(jwtTokenProvider.getUsername(token));
        if (!StringUtils.isEmpty(updateUser.getUsername())) {
            userFromDb.setUsername(updateUser.getUsername());
        }

        if (!StringUtils.isEmpty(updateUser.getFirstName())) {
            userFromDb.setFirstName(updateUser.getFirstName());
        }

        if (!StringUtils.isEmpty(updateUser.getSeconfName())) {
            userFromDb.setSecondName(updateUser.getSeconfName());
        }

        if (!StringUtils.isEmpty(updateUser.getEmail())) {
            userFromDb.setEmail(updateUser.getEmail());
        }

        return userRepo.save(userFromDb);
    }
}
