package org.example.springBootApp.springSecurityExercises.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}

