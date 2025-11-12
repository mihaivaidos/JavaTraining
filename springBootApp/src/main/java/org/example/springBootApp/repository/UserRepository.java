package org.example.springBootApp.repository;

import org.example.springBootApp.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
}
