package org.example.springBootApp.springDataJPAExercises.mapper;

import org.example.springBootApp.springDataJPAExercises.dto.UserCreateDto;
import org.example.springBootApp.springDataJPAExercises.dto.UserDto;
import org.example.springBootApp.springDataJPAExercises.dto.UserUpdateDto;
import org.example.springBootApp.springDataJPAExercises.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();
    }

    public User toEntity(UserCreateDto userCreateDto) {
        if (userCreateDto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setEmail(userCreateDto.getEmail());
        user.setPassword(userCreateDto.getPassword());
        user.setFirstname(userCreateDto.getFirstname());
        user.setLastname(userCreateDto.getLastname());
        return user;
    }

    public void updateEntityFromDto(UserUpdateDto userUpdateDto, User user) {
        if (userUpdateDto == null || user == null) {
            return;
        }
        if (userUpdateDto.getUsername() != null) {
            user.setUsername(userUpdateDto.getUsername());
        }
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getPassword() != null) {
            user.setPassword(userUpdateDto.getPassword());
        }
        if (userUpdateDto.getFirstname() != null) {
            user.setFirstname(userUpdateDto.getFirstname());
        }
        if (userUpdateDto.getLastname() != null) {
            user.setLastname(userUpdateDto.getLastname());
        }
    }
}

