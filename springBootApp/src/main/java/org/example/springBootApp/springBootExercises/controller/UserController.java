package org.example.springBootApp.springBootExercises.controller;

import jakarta.validation.constraints.Size;
import org.example.springBootApp.springBootExercises.model.User;
import org.example.springBootApp.springBootExercises.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Controller: Received GET request for all users.");
        List<User> users = userService.getAllUsers();
        logger.info("Controller: Responding with {} total users.", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> getUsersByName(
            @RequestParam
            @Size(min = 2, max = 50, message = "Name parameter must be between 2 and 50 characters")
            String name) {
        logger.info("Controller: Received GET request to search for users with name: {}", name);
        List<User> users = userService.getUsersByName(name);
        logger.info("Controller: Responding with {} users found by name.", users.size());
        return ResponseEntity.ok(users);
    }
}