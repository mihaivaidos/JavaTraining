package org.example.springBootApp.springDataJPAExercises.repository;

import org.example.springBootApp.springDataJPAExercises.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User findByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u")
    long countUsers();
}
