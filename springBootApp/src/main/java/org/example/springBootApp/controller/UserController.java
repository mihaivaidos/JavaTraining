package org.example.springBootApp.controller;

import jakarta.validation.constraints.Size;
import org.example.springBootApp.model.User;
import org.example.springBootApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public List<User> getAllUsers(
            @RequestParam(required = false)
            @Size(min = 2, max = 50, message = "Name parameter must be between 2 and 50 characters")
            String name) {
        logger.info("Controller: Received GET request for all users with name filter: {}", name);
        List<User> users = userService.getUsersByName(name);

        logger.info("Controller: Responding with {} users.", users.size());
        return users;
    }
}