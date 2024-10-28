package com.extraAllt.extraAllt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.extraAllt.extraAllt.models.LoginRequest;
import com.extraAllt.extraAllt.models.LoginResponse;
import com.extraAllt.extraAllt.models.UpdatePointsRequest;
import com.extraAllt.extraAllt.models.User;
import com.extraAllt.extraAllt.services.UserService;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
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

    //hämta alla users
    @GetMapping("/get-all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //updatera user points
    @PostMapping("/update-points")
    public void updatePoints(@RequestBody UpdatePointsRequest updatePointsRequest) {
        userService.updatePoints(updatePointsRequest.getPoints(), updatePointsRequest.getUserId());
    }
}
