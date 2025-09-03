package com.berru.app.bankingapplication.repository;

import com.berru.app.bankingapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
