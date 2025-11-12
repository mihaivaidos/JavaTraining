package org.example.springBootApp.repository;

import org.example.springBootApp.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final List<User> users = new ArrayList<>();

    public UserRepositoryImpl() {
        users.add(new User(1, "Alice", "alice@gmail.com"));
        users.add(new User(2, "Bob", "bob@gmail.com"));
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}
