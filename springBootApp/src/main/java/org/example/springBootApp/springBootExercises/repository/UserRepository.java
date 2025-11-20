package org.example.springBootApp.springBootExercises.repository;

import org.example.springBootApp.springBootExercises.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    List<User> getUsersByName(String name);
}
