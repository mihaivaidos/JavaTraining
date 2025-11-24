package org.example.springBootApp.springDataJPAExercises.service;

import org.example.springBootApp.springDataJPAExercises.model.User;
import org.example.springBootApp.springDataJPAExercises.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(1L, "alice", "alice@example.com", "password123", "Alice", "Smith");
        user2 = new User(2L, "bob", "bob@example.com", "password456", "Bob", "Johnson");
    }

    @Test
    void saveUser_shouldSaveAndReturnUser() {
        when(userRepository.save(any(User.class))).thenReturn(user1);
        User savedUser = userService.saveUser(user1);
        assertNotNull(savedUser);
        assertEquals("alice", savedUser.getUsername());
        assertEquals("Alice", savedUser.getFirstname());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        Optional<User> foundUser = userService.getUserById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals(user1, foundUser.get());
        assertEquals("alice", foundUser.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);
        List<User> foundUsers = userService.getAllUsers();

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        assertTrue(foundUsers.stream().anyMatch(u -> u.getFirstname().equals("Alice")));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getFirstname().equals("Bob")));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getUsername().equals("alice")));
        assertTrue(foundUsers.stream().anyMatch(u -> u.getUsername().equals("bob")));

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser() {
        user1.setEmail("alice.new@example.com");
        when(userRepository.save(any(User.class))).thenReturn(user1);
        User updatedUser = userService.updateUser(user1);

        assertNotNull(updatedUser);
        assertEquals("alice.new@example.com", updatedUser.getEmail());
        assertEquals("alice", updatedUser.getUsername());

        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void deleteUser_shouldCallDeleteById() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void getUserByUsername_shouldReturnUser_whenUserExists() {
        when(userRepository.findByUsername("alice")).thenReturn(user1);
        User foundUser = userService.getUserByUsername("alice");

        assertNotNull(foundUser);
        assertEquals("alice", foundUser.getUsername());
        assertEquals("Alice", foundUser.getFirstname());
        assertEquals("alice@example.com", foundUser.getEmail());

        verify(userRepository, times(1)).findByUsername("alice");
    }

    @Test
    void getUserByEmail_shouldReturnUser_whenUserExists() {
        when(userRepository.findByEmail("alice@example.com")).thenReturn(user1);
        User foundUser = userService.getUserByEmail("alice@example.com");

        assertNotNull(foundUser);
        assertEquals("alice@example.com", foundUser.getEmail());
        assertEquals("alice", foundUser.getUsername());

        verify(userRepository, times(1)).findByEmail("alice@example.com");
    }

    @Test
    void countUsers_shouldReturnUserCount() {
        when(userRepository.countUsers()).thenReturn(10L);
        long userCount = userService.countUsers();

        assertEquals(10L, userCount);

        verify(userRepository, times(1)).countUsers();
    }
}
