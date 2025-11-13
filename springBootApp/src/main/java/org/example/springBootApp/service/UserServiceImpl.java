package org.example.springBootApp.service;

import org.example.springBootApp.model.User;
import org.example.springBootApp.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Service: Getting all users.");
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByName(String name) {
        List<User> users = getAllUsers();
        if (name == null || name.trim().isEmpty()) {
            return users;
        }
        return users.stream()
                .filter(user -> user.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
}
