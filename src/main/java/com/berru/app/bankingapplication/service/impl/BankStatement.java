package com.berru.app.bankingapplication.service.impl;

import com.berru.app.bankingapplication.entity.Transaction;
import com.berru.app.bankingapplication.entity.User;
import com.berru.app.bankingapplication.exception.BadRequestException;
import com.berru.app.bankingapplication.repository.TransactionRepository;
import com.berru.app.bankingapplication.repository.UserRepository;
import com.berru.app.bankingapplication.utils.PdfGenerator;
import com.itextpdf.text.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

import java.util.List;
import com.itextpdf.text.DocumentException;


@Service
@Slf4j
public class BankStatement {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public BankStatement(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public void generateStatement(String accountNumber, String startDate, String endDate, OutputStream outputStream)
            throws IOException, DocumentException {
        User user = userRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BadRequestException("Invalid account number"));

        List<Transaction> transactions = transactionRepository.findByAccountNumberAndCreatedAtBetween(
                accountNumber,
                LocalDate.parse(startDate).atStartOfDay(),
                LocalDate.parse(endDate).atTime(23, 59, 59)
        );

        PdfGenerator.generateBankStatement(transactions, user, startDate, endDate, outputStream);
    }
}
