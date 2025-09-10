package com.berru.app.bankingapplication.controller;

import com.berru.app.bankingapplication.dto.BankResponseDTO;
import com.berru.app.bankingapplication.dto.CreateUserRequestDTO;
import com.berru.app.bankingapplication.dto.CreditDebitRequestDTO;
import com.berru.app.bankingapplication.dto.TransferInfoRequestDTO;
import com.berru.app.bankingapplication.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<BankResponseDTO> createAccount(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        BankResponseDTO response = userService.createAccount(createUserRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balanceEnquiry/{accountNumber}")
    public ResponseEntity<BankResponseDTO> balanceEnquiry(@PathVariable String accountNumber) {
        BankResponseDTO response = userService.balanceEnquiry(accountNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nameEnquiry/{accountNumber}")
    public ResponseEntity<String> nameEnquiry(@PathVariable String accountNumber) {
        String name = userService.nameEnquiry(accountNumber);
        return ResponseEntity.ok(name);
    }

    @PostMapping("/credit")
    public ResponseEntity<BankResponseDTO> creditAccount(@RequestBody CreditDebitRequestDTO creditDebitRequestDTO) {
        BankResponseDTO response = userService.creditAccount(creditDebitRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/debit")
    public ResponseEntity<BankResponseDTO> debitAccount(@RequestBody CreditDebitRequestDTO creditDebitRequestDTO) {
        BankResponseDTO response = userService.debitAccount(creditDebitRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<BankResponseDTO> transfer(@RequestBody TransferInfoRequestDTO transferInfoRequestDTO) {
        BankResponseDTO response = userService.transfer(transferInfoRequestDTO);
        return ResponseEntity.ok(response);
    }
}
