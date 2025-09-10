package com.berru.app.bankingapplication.service.impl;

import com.berru.app.bankingapplication.dto.TransactionRequestDTO;
import com.berru.app.bankingapplication.entity.Transaction;
import com.berru.app.bankingapplication.mapper.TransactionMapper;
import com.berru.app.bankingapplication.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public void saveTransaction(TransactionRequestDTO  transactionRequestDTO) {
    Transaction transaction = transactionMapper.toEntity(transactionRequestDTO);
    transactionRepository.save(transaction);
    System.out.println("Transaction saved successfully");
    }
}
