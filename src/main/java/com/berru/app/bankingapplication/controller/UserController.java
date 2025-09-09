package com.berru.app.bankingapplication.controller;

import com.berru.app.bankingapplication.dto.BankResponse;
import com.berru.app.bankingapplication.dto.CreateUserRequestDTO;
import com.berru.app.bankingapplication.dto.CreditDebitRequest;
import com.berru.app.bankingapplication.dto.TransferInfo;
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
    public ResponseEntity<BankResponse> createAccount(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        BankResponse response = userService.createAccount(createUserRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balanceEnquiry/{accountNumber}")
    public ResponseEntity<BankResponse> balanceEnquiry(@PathVariable String accountNumber) {
        BankResponse response = userService.balanceEnquiry(accountNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nameEnquiry/{accountNumber}")
    public ResponseEntity<String> nameEnquiry(@PathVariable String accountNumber) {
        String name = userService.nameEnquiry(accountNumber);
        return ResponseEntity.ok(name);
    }

    @PostMapping("/credit")
    public ResponseEntity<BankResponse> creditAccount(@RequestBody CreditDebitRequest creditDebitRequest) {
        BankResponse response = userService.creditAccount(creditDebitRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/debit")
    public ResponseEntity<BankResponse> debitAccount(@RequestBody CreditDebitRequest creditDebitRequest) {
        BankResponse response = userService.debitAccount(creditDebitRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<BankResponse> transfer(@RequestBody TransferInfo transferInfo) {
        BankResponse response = userService.transfer(transferInfo);
        return ResponseEntity.ok(response);
    }
}
