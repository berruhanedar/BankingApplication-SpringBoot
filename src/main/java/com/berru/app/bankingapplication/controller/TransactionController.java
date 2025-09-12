package com.berru.app.bankingapplication.controller;

import com.berru.app.bankingapplication.service.impl.BankStatement;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/bankstatement")
public class TransactionController {

    private final BankStatement bankStatement;

    @Autowired
    public TransactionController(BankStatement bankStatement) {
        this.bankStatement = bankStatement;
    }

    @GetMapping
    public void getStatement(
            @RequestParam String accountNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletResponse response
    ) throws IOException, com.itextpdf.text.DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"statement.pdf\"");
        bankStatement.generateStatement(accountNumber, startDate.toString(), endDate.toString(), response.getOutputStream());
    }
}
