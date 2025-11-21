package org.example.springBootApp.springSecurityExercises.controller;

import jakarta.validation.Valid;
import org.example.springBootApp.springSecurityExercises.dto.SignInRequest;
import org.example.springBootApp.springSecurityExercises.dto.SignInResponse;
import org.example.springBootApp.springSecurityExercises.dto.SignUpRequest;
import org.example.springBootApp.springSecurityExercises.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<SignInResponse> login(@Valid @RequestBody SignInRequest signInRequest) {
        SignInResponse response = authService.signIn(signInRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<SignInResponse> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        SignInResponse response = authService.signUp(signUpRequest);
        return ResponseEntity.ok(response);
    }
}

