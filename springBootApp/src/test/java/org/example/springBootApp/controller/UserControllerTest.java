package org.example.springBootApp.controller;

import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import org.example.springBootApp.model.User;
import org.example.springBootApp.service.UserService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void getAllUsers_shouldReturnUserList() throws Exception {
        User user1 = new User(1, "Mihai", "mihai@gmail.com");
        User user2 = new User(2, "Stefan", "stefan@gmail.com");
        List<User> users = Arrays.asList(user1, user2);

        when(userService.getUsersByName(null)).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Mihai"))
                .andExpect(jsonPath("$[1].name").value("Stefan"));
    }

    @Test
    public void getAllUsers_withValidNameParam_shouldReturnFilteredList() throws Exception {
        User user1 = new User(1, "Mihai", "mihai@gmail.com");
        List<User> filteredUsers = List.of(user1);

        when(userService.getUsersByName("Mihai")).thenReturn(filteredUsers);

        mockMvc.perform(get("/users").param("name", "Mihai"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Mihai"));
    }

    @Test
    public void getAllUsers_withTooShortNameParam_shouldThrowException() {
        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(get("/users").param("name", "a"))
        );
        assertInstanceOf(ConstraintViolationException.class, exception.getCause());
    }

    @Test
    public void getAllUsers_withTooLongNameParam_shouldThrowException() {
        String longName = "a".repeat(51);
        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(get("/users").param("name", longName))
        );
        assertInstanceOf(ConstraintViolationException.class, exception.getCause());
    }

    @Test
    public void getAllUsers_withEmptyNameParam_shouldThrowException() {
        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(get("/users").param("name", ""))
        );
        assertInstanceOf(ConstraintViolationException.class, exception.getCause());
    }

    @Test
    public void getAllUsers_withoutNameParam_shouldReturnAllUsers() throws Exception {
        User user1 = new User(1, "Mihai", "mihai@gmail.com");
        User user2 = new User(2, "Stefan", "stefan@gmail.com");
        List<User> users = Arrays.asList(user1, user2);

        when(userService.getUsersByName(null)).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

}
