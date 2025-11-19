package org.example.springBootApp.springDataJPAExercises.service;

import org.example.springBootApp.springDataJPAExercises.model.User;
import org.example.springBootApp.springDataJPAExercises.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> updateUser(Integer id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setEmail(userDetails.getEmail());
                    user.setPassword(userDetails.getPassword());
                    user.setFirstname(userDetails.getFirstname());
                    user.setLastname(userDetails.getLastname());
                    return userRepository.save(user);
                });
    }

    public boolean deleteUser(Integer id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }

    public Optional<User> partialUpdateUser(Integer id, Map<String, Object> updates) {
        return userRepository.findById(id)
                .map(user -> {
                    updates.forEach((key, value) -> {
                        switch (key) {
                            case "username":
                                user.setUsername((String) value);
                                break;
                            case "email":
                                user.setEmail((String) value);
                                break;
                            case "password":
                                user.setPassword((String) value);
                                break;
                            case "firstname":
                                user.setFirstname((String) value);
                                break;
                            case "lastname":
                                user.setLastname((String) value);
                                break;
                        }
                    });
                    return userRepository.save(user);
                });
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public long countUsers() {
        return userRepository.countUsers();
    }
}
