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

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1, "testuser", "test@example.com", "password", "Test", "User");
    }

    @Test
    void saveUser_shouldSaveAndReturnUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User savedUser = userService.saveUser(user);
        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.getUserById(1);
        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        User user2 = new User(2, "testuser2", "test2@example.com", "password2", "Test2", "User2");
        List<User> users = Arrays.asList(user, user2);
        when(userRepository.findAll()).thenReturn(users);
        List<User> foundUsers = userService.getAllUsers();
        assertEquals(2, foundUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        user.setEmail("new.email@example.com");
        User updatedUser = userService.updateUser(user);
        assertNotNull(updatedUser);
        assertEquals("new.email@example.com", updatedUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUser_shouldCallDeleteById() {
        doNothing().when(userRepository).deleteById(1);
        userService.deleteUser(1);
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void getUserByUsername_shouldReturnUser_whenUserExists() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        User foundUser = userService.getUserByUsername("testuser");
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void getUserByEmail_shouldReturnUser_whenUserExists() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        User foundUser = userService.getUserByEmail("test@example.com");
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void countUsers_shouldReturnUserCount() {
        when(userRepository.countUsers()).thenReturn(10L);
        long userCount = userService.countUsers();
        assertEquals(10L, userCount);
        verify(userRepository, times(1)).countUsers();
    }
}
