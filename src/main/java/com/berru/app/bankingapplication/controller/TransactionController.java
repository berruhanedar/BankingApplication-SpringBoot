package com.berru.app.bankingapplication.controller;

import com.berru.app.bankingapplication.entity.Transaction;
import com.berru.app.bankingapplication.service.impl.BankStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bankstatement")
public class TransactionController {

    private final BankStatement bankStatement;

    @Autowired
    public TransactionController(BankStatement bankStatement) {
        this.bankStatement = bankStatement;
    }

    @GetMapping
    public List<Transaction> getStatement(
            @RequestParam String accountNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return bankStatement.generateStatement(
                accountNumber,
                startDate.toString(),
                endDate.toString()
        );
    }
}
