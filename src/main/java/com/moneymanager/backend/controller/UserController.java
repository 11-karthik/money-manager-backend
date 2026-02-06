package com.moneymanager.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.moneymanager.backend.model.User;
import com.moneymanager.backend.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ==========================
    // SIGN UP
    // ==========================
    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return userService.signup(user);
    }

    // ==========================
    // SIGN IN
    // ==========================
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user.getEmail(), user.getPassword());
    }
}
