package org.example.springBootApp.springDataJPAExercises.service;

import org.example.springBootApp.springDataJPAExercises.dto.UserCreateDto;
import org.example.springBootApp.springDataJPAExercises.dto.UserDto;
import org.example.springBootApp.springDataJPAExercises.dto.UserUpdateDto;
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
        User user = userMapper.toEntity(createDto);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }

    public Optional<UserDto> updateUser(Long id, UserUpdateDto updateDto) {
        return userRepository.findById(id)
                .map(existing -> {
                    userMapper.updateEntityFromDto(updateDto, existing);
                    User saved = userRepository.save(existing);
                    return userMapper.toDto(saved);
                });
    }

    public boolean deleteUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }

    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto);
    }

    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto);
    }

    public long countUsers() {
        return userRepository.countUsers();
    }
}
