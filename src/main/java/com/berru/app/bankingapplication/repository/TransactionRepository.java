package com.berru.app.bankingapplication.repository;

import com.berru.app.bankingapplication.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByAccountNumberAndCreatedAtBetween(
            String accountNumber, LocalDateTime start, LocalDateTime end);
}
