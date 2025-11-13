package org.example.springBootApp.repository;

import org.example.springBootApp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserRepositoryImplTest {

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
        userRepository.init();
    }

    @Test
    void findAll_shouldReturnInitializedUsers() {
        List<User> users = userRepository.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("Alice", users.get(0).getName());
        assertEquals("alice@gmail.com", users.get(0).getEmail());
        assertEquals("Bob", users.get(1).getName());
        assertEquals("bob@gmail.com", users.get(1).getEmail());
    }
}