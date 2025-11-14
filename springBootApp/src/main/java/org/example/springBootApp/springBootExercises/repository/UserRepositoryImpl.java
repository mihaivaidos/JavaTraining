package org.example.springBootApp.springBootExercises.repository;

import jakarta.annotation.PostConstruct;
import org.example.springBootApp.springBootExercises.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final List<User> users = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @PostConstruct
    public void init() {
        logger.info("Initializing UserRepositoryImpl with sample data");
        users.add(new User(1, "Alice", "alice@gmail.com"));
        users.add(new User(2, "Bob", "bob@gmail.com"));
    }

    @Override
    public List<User> findAll() {
        logger.info("Repository: Fetching all users. Found {} users.", users.size());
        return users;
    }
}
