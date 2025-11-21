package org.example.springBootApp.springBootExercises.mapper;

import org.example.springBootApp.springBootExercises.dto.UserDto;
import org.example.springBootApp.springBootExercises.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}

