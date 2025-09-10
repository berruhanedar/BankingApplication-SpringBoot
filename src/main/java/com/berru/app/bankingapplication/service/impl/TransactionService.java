package com.berru.app.bankingapplication.service.impl;

import com.berru.app.bankingapplication.dto.TransactionRequestDTO;

public interface TransactionService {
    void saveTransaction(TransactionRequestDTO transactionRequestDTO);
}
