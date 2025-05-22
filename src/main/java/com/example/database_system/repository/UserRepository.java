package com.example.database_system.repository;

import com.example.database_system.pojo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByaccount(String account);
}
