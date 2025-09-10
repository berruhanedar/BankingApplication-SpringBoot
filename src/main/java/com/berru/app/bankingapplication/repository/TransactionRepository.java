package com.berru.app.bankingapplication.repository;

import com.berru.app.bankingapplication.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
