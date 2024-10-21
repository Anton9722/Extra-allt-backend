package com.extraAllt.extraAllt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.extraAllt.extraAllt.models.LoginRequest;
import com.extraAllt.extraAllt.models.LoginResponse;
import com.extraAllt.extraAllt.models.User;
import com.extraAllt.extraAllt.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    //skapa nytt konto
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    //inloggning
    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        return userService.loginUser(username, password);
    }
}
