package org.example.springBootApp.springDataJPAExercises.service;

import org.example.springBootApp.springDataJPAExercises.dto.UserCreateDto;
import org.example.springBootApp.springDataJPAExercises.dto.UserDto;
import org.example.springBootApp.springDataJPAExercises.dto.UserPatchDto;
import org.example.springBootApp.springDataJPAExercises.dto.UserPutDto;
import org.example.springBootApp.springDataJPAExercises.exception.UserNotFoundException;
import org.example.springBootApp.springDataJPAExercises.mapper.UserMapper;
import org.example.springBootApp.springDataJPAExercises.model.User;
import org.example.springBootApp.springDataJPAExercises.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto saveUser(UserCreateDto createDto) {
        return userMapper.mapUserToUserDto(userRepository.save(userMapper.mapUserCreateDtoToUser(createDto)));
    }

    public Optional<UserDto> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .map(userMapper::mapUserToUserDto)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found")));
    }

    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::mapUserToUserDto);
    }

    public Optional<UserDto> updateUser(Long id, UserPutDto userPutDto) {
        User existing = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        userMapper.updateUserFromUserPutDto(userPutDto, existing);

        User updated = userRepository.save(existing);
        return Optional.ofNullable(userMapper.mapUserToUserDto(updated));
    }

    public Optional<UserDto> updateUser(Long id, UserPatchDto userPatchDto) {
        User existing = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        userMapper.updateUserFromUserPatchDto(userPatchDto, existing);

        User updated = userRepository.save(existing);
        return Optional.ofNullable(userMapper.mapUserToUserDto(updated));
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::mapUserToUserDto);
    }

    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::mapUserToUserDto);
    }

    public long countUsers() {
        return userRepository.countUsers();
    }
}
