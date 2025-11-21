package org.example.springBootApp.springSecurityExercises.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.springBootApp.springSecurityExercises.dto.SignInRequest;
import org.example.springBootApp.springSecurityExercises.dto.SignInResponse;
import org.example.springBootApp.springSecurityExercises.dto.SignUpRequest;
import org.example.springBootApp.springSecurityExercises.model.Role;
import org.example.springBootApp.springSecurityExercises.model.RoleName;
import org.example.springBootApp.springSecurityExercises.model.User;
import org.example.springBootApp.springSecurityExercises.repository.RoleRepository;
import org.example.springBootApp.springSecurityExercises.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SecretKey secretKey;
    private final long jwtExpirationMs = 86400000; // 24 hours

    public AuthServiceImpl(UserRepository userRepository,
                          RoleRepository roleRepository,
                          @Value("${jwt.secret:mySecretKeyForJWTTokenGenerationThatIsAtLeast256BitsLong}") String secret) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String storedPassword = user.getPassword().replace("{noop}", "");
        if (!storedPassword.equals(signInRequest.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String token = generateToken(user.getUsername());
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());

        return new SignInResponse(token, roles);
    }

    @Override
    public SignInResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new RuntimeException("USER role not found"));

        User newUser = new User();
        newUser.setUsername(signUpRequest.getUsername());
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setPassword("{noop}" + signUpRequest.getPassword());
        newUser.setRoles(Set.of(userRole));

        userRepository.save(newUser);

        String token = generateToken(newUser.getUsername());
        List<String> roles = List.of(userRole.getName().name());

        return new SignInResponse(token, roles);
    }

    @Override
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
}

