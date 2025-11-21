package org.example.springBootApp.repository;

import org.example.springBootApp.springBootExercises.model.User;
import org.example.springBootApp.springBootExercises.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
        userRepository.init();
    }

    @Test
    void findAll_shouldReturnAllInitializedUsers() {
        List<User> users = userRepository.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("Alice")));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("Bob")));
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals("alice@gmail.com")));
        assertTrue(users.stream().anyMatch(u -> u.getEmail().equals("bob@gmail.com")));
    }

    @Test
    void getUsersByName_shouldReturnMatchingUsers() {
        List<User> users = userRepository.getUsersByName("Alice");

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("Alice", users.get(0).getName());
        assertEquals("alice@gmail.com", users.get(0).getEmail());
    }

    @Test
    void getUsersByName_shouldReturnMultipleMatchingUsers() {
        List<User> users = userRepository.getUsersByName("o");

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("Bob")));
    }

    @Test
    void getUsersByName_shouldReturnEmptyListWhenNoMatch() {
        List<User> users = userRepository.getUsersByName("NonExistent");

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    void getUsersByName_withNullName_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> userRepository.getUsersByName(null));
    }
}