package org.example.springBootApp.springBootExercises.repository;

import jakarta.annotation.PostConstruct;
import org.example.springBootApp.springBootExercises.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final List<User> users = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @PostConstruct
    public void init() {
        logger.info("Initializing UserRepositoryImpl with sample data");
        users.add(new User(1L, "Alice", "alice@gmail.com"));
        users.add(new User(2L, "Bob", "bob@gmail.com"));
    }

    @Override
    public List<User> findAll() {
        logger.info("Repository: Fetching all users. Found {} users.", users.size());
        return new ArrayList<>(users);
    }

    @Override
    public List<User> getUsersByName(@NonNull String name) {
        Objects.requireNonNull(name, "Name parameter cannot be null");
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(user);
            }
        }
        logger.info("Repository: Found {} users matching name filter: {}", result.size(), name);
        return result;
    }
}
