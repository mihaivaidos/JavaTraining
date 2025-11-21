package org.example.springBootApp.service;

import org.example.springBootApp.springBootExercises.dto.UserDto;
import org.example.springBootApp.springBootExercises.mapper.UserMapper;
import org.example.springBootApp.springBootExercises.model.User;
import org.example.springBootApp.springBootExercises.repository.UserRepository;
import org.example.springBootApp.springBootExercises.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private List<User> mockUsers;
    private List<UserDto> mockUserDtos;

    @BeforeEach
    void setUp() {
        mockUsers = Arrays.asList(
                new User(1L, "Alice", "alice@gmail.com"),
                new User(2L, "Bob", "bob@gmail.com")
        );

        mockUserDtos = Arrays.asList(
                new UserDto(1L, "Alice", "alice@gmail.com"),
                new UserDto(2L, "Bob", "bob@gmail.com")
        );
    }

    @Test
    void getAllUsers_shouldReturnListOfUserDtos() {
        when(userRepository.findAll()).thenReturn(mockUsers);
        when(userMapper.toDto(mockUsers.get(0))).thenReturn(mockUserDtos.get(0));
        when(userMapper.toDto(mockUsers.get(1))).thenReturn(mockUserDtos.get(1));

        List<UserDto> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(u -> u.name().equals("Alice")));
        assertTrue(result.stream().anyMatch(u -> u.name().equals("Bob")));
        assertTrue(result.stream().anyMatch(u -> u.email().equals("alice@gmail.com")));
        assertTrue(result.stream().anyMatch(u -> u.email().equals("bob@gmail.com")));
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(2)).toDto(any(User.class));
    }

    @Test
    void getUsersByName_shouldReturnFilteredListOfUserDtos() {
        User aliceUser = new User(1L, "Alice", "alice@gmail.com");
        UserDto aliceDto = new UserDto(1L, "Alice", "alice@gmail.com");

        when(userRepository.getUsersByName("Alice")).thenReturn(Collections.singletonList(aliceUser));
        when(userMapper.toDto(aliceUser)).thenReturn(aliceDto);

        List<UserDto> result = userService.getUsersByName("Alice");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(u -> u.name().equals("Alice")));
        assertTrue(result.stream().anyMatch(u -> u.email().equals("alice@gmail.com")));
        verify(userRepository, times(1)).getUsersByName("Alice");
        verify(userMapper, times(1)).toDto(aliceUser);
    }

    @Test
    void getUsersByName_shouldReturnEmptyListWhenNoMatch() {
        when(userRepository.getUsersByName("NonExistent")).thenReturn(Collections.emptyList());

        List<UserDto> result = userService.getUsersByName("NonExistent");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).getUsersByName("NonExistent");
        verify(userMapper, never()).toDto(any(User.class));
    }

    @Test
    void getUsersByName_withNullName_shouldThrowException() {
        when(userRepository.getUsersByName(null)).thenThrow(new NullPointerException("Name parameter cannot be null"));

        assertThrows(NullPointerException.class, () -> userService.getUsersByName(null));
        verify(userRepository, times(1)).getUsersByName(null);
    }
}