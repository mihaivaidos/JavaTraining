package org.example.springBootApp.springSecurityExercises.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public String getUsers() {
        return "List of users (requires authentication)";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        return "User deleted: " + id;
    }
}