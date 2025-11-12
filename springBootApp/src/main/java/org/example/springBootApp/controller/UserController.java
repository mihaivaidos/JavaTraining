package org.example.springBootApp.controller;

import org.example.springBootApp.model.User;
import org.example.springBootApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Controller: Received GET request for all users.");
        List<User> users = userService.getAllUsers();
        logger.info("Controller: Responding with {} users.", users.size());
        return users;
    }
}