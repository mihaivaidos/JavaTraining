package org.example.springBootApp.springSecurityExercises.config;

import org.example.springBootApp.springSecurityExercises.model.Role;
import org.example.springBootApp.springSecurityExercises.model.RoleName;
import org.example.springBootApp.springSecurityExercises.model.User;
import org.example.springBootApp.springSecurityExercises.repository.RoleRepository;
import org.example.springBootApp.springSecurityExercises.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName(RoleName.ADMIN);
                        return roleRepository.save(role);
                    });

            Role userRole = roleRepository.findByName(RoleName.USER)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName(RoleName.USER);
                        return roleRepository.save(role);
                    });

            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@test.com");
                admin.setRoles(Set.of(adminRole));
                userRepository.save(admin);
                System.out.println("Created admin user: username=admin, password=admin123");
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setEmail("user@test.com");
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
                System.out.println("Created regular user: username=user, password=user123");
            }
        };
    }
}

