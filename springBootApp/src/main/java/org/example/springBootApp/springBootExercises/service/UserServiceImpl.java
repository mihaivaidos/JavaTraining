package org.example.springBootApp.springBootExercises.service;

import org.example.springBootApp.springBootExercises.dto.UserDto;
import org.example.springBootApp.springBootExercises.mapper.UserMapper;
import org.example.springBootApp.springBootExercises.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        logger.info("Service: Getting all users.");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersByName(String name) {
        logger.info("Service: Getting users by name filter: {}", name);
        return userRepository.getUsersByName(name)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
