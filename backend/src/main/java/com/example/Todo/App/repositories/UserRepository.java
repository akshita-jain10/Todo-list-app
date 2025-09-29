package com.example.Todo.App.repositories;

import com.example.Todo.App.entities.User;
import com.example.Todo.App.enums.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User>findFirstByEmail(String username);

    Optional<User> findByUserRole(UserRole userRole);

    Optional<User> findByEmail(String username);
}
