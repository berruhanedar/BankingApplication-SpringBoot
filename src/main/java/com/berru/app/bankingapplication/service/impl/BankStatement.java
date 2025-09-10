package com.berru.app.bankingapplication.service.impl;

import com.berru.app.bankingapplication.entity.Transaction;
import com.berru.app.bankingapplication.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankStatement {
    private final TransactionRepository transactionRepository;

    @Autowired
    public BankStatement(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) {
        LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate).atTime(23, 59, 59);

        return transactionRepository.findByAccountNumberAndCreatedAtBetween(accountNumber, start, end);
    }
}
