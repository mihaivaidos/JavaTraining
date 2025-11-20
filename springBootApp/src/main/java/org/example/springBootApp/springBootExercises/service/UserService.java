package org.example.springBootApp.springBootExercises.service;

import org.example.springBootApp.springBootExercises.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    List<UserDto> getUsersByName(String name);
}
