package org.example.springBootApp.controller;

import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import org.example.springBootApp.springBootExercises.controller.UserController;
import org.example.springBootApp.springBootExercises.dto.UserDto;
import org.example.springBootApp.springBootExercises.service.UserService;
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
        UserDto user1 = new UserDto(1L, "Mihai", "mihai@gmail.com");
        UserDto user2 = new UserDto(2L, "Stefan", "stefan@gmail.com");
        List<UserDto> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[*].name").value(org.hamcrest.Matchers.containsInAnyOrder("Mihai", "Stefan")))
                .andExpect(jsonPath("$[*].email").value(org.hamcrest.Matchers.containsInAnyOrder("mihai@gmail.com", "stefan@gmail.com")));
    }

    @Test
    public void getUsersByName_withValidNameParam_shouldReturnFilteredList() throws Exception {
        UserDto user1 = new UserDto(1L, "Mihai", "mihai@gmail.com");
        List<UserDto> filteredUsers = List.of(user1);

        when(userService.getUsersByName("Mihai")).thenReturn(filteredUsers);

        mockMvc.perform(get("/users/search").param("name", "Mihai"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[*].name").value(org.hamcrest.Matchers.containsInAnyOrder("Mihai")))
                .andExpect(jsonPath("$[*].email").value(org.hamcrest.Matchers.containsInAnyOrder("mihai@gmail.com")));
    }

    @Test
    public void getUsersByName_withTooShortNameParam_shouldThrowException() {
        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(get("/users/search").param("name", "a"))
        );
        assertInstanceOf(ConstraintViolationException.class, exception.getCause());
    }

    @Test
    public void getUsersByName_withTooLongNameParam_shouldThrowException() {
        String longName = "a".repeat(51);
        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(get("/users/search").param("name", longName))
        );
        assertInstanceOf(ConstraintViolationException.class, exception.getCause());
    }

    @Test
    public void getUsersByName_withEmptyNameParam_shouldThrowException() {
        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(get("/users/search").param("name", ""))
        );
        assertInstanceOf(ConstraintViolationException.class, exception.getCause());
    }

    @Test
    public void getAllUsers_withoutNameParam_shouldReturnAllUsers() throws Exception {
        UserDto user1 = new UserDto(1L, "Mihai", "mihai@gmail.com");
        UserDto user2 = new UserDto(2L, "Stefan", "stefan@gmail.com");
        List<UserDto> users = Arrays.asList(user1, user2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[*].name").value(org.hamcrest.Matchers.containsInAnyOrder("Mihai", "Stefan")))
                .andExpect(jsonPath("$[*].email").value(org.hamcrest.Matchers.containsInAnyOrder("mihai@gmail.com", "stefan@gmail.com")));
    }

}
