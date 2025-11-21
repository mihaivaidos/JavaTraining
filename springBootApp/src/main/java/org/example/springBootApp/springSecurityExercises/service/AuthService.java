package org.example.springBootApp.springSecurityExercises.service;

import org.example.springBootApp.springSecurityExercises.dto.SignInRequest;
import org.example.springBootApp.springSecurityExercises.dto.SignInResponse;
import org.example.springBootApp.springSecurityExercises.dto.SignUpRequest;

public interface AuthService {
    SignInResponse signIn(SignInRequest signInRequest);
    SignInResponse signUp(SignUpRequest signUpRequest);
    String generateToken(String username);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}

