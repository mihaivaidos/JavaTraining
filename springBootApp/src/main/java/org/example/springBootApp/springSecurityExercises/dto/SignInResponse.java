package org.example.springBootApp.springSecurityExercises.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {

    private String token;
    private List<String> roles;
}

