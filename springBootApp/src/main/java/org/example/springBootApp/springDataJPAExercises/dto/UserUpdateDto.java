package org.example.springBootApp.springDataJPAExercises.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstname;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastname;
}

