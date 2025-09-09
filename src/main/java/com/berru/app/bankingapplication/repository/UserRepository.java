package com.berru.app.bankingapplication.repository;

import com.berru.app.bankingapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Optional<User> findByAccountNumber(String accountNumber);
}
