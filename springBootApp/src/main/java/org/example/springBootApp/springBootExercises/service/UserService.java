package org.example.springBootApp.springBootExercises.service;

import org.example.springBootApp.springBootExercises.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    List<User> getUsersByName(String name);
}
