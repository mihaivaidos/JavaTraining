package org.example.springBootApp.springSecurityExercises.repository;

import org.example.springBootApp.springSecurityExercises.model.Role;
import org.example.springBootApp.springSecurityExercises.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, RoleName> {
    Optional<Role> findByName(RoleName name);
}