package org.example.springBootApp.springDataJPAExercises.mapper;

import org.example.springBootApp.springDataJPAExercises.dto.UserCreateDto;
import org.example.springBootApp.springDataJPAExercises.dto.UserDto;
import org.example.springBootApp.springDataJPAExercises.dto.UserPatchDto;
import org.example.springBootApp.springDataJPAExercises.dto.UserPutDto;
import org.example.springBootApp.springDataJPAExercises.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto mapUserToUserDto(User user) {
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

    public User mapUserCreateDtoToUser(UserCreateDto userCreateDto) {
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

    public void updateUserFromUserPutDto(UserPutDto userPutDto, User user) {
        if (userPutDto == null || user == null) {
            return;
        }
        user.setUsername(userPutDto.getUsername());
        user.setEmail(userPutDto.getEmail());
        user.setPassword(userPutDto.getPassword());
        user.setFirstname(userPutDto.getFirstname());
        user.setLastname(userPutDto.getLastname());
    }

    public void updateUserFromUserPatchDto(UserPatchDto userPatchDto, User user) {
        if (userPatchDto == null || user == null) {
            return;
        }
        if (userPatchDto.getUsername() != null) {
            user.setUsername(userPatchDto.getUsername());
        }
        if (userPatchDto.getEmail() != null) {
            user.setEmail(userPatchDto.getEmail());
        }
        if (userPatchDto.getPassword() != null) {
            user.setPassword(userPatchDto.getPassword());
        }
        if (userPatchDto.getFirstname() != null) {
            user.setFirstname(userPatchDto.getFirstname());
        }
        if (userPatchDto.getLastname() != null) {
            user.setLastname(userPatchDto.getLastname());
        }
    }
}

