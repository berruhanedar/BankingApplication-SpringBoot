package com.berru.app.bankingapplication.controller;

import com.berru.app.bankingapplication.dto.BankResponse;
import com.berru.app.bankingapplication.dto.CreateUserRequestDTO;
import com.berru.app.bankingapplication.dto.CreditDebitRequest;
import com.berru.app.bankingapplication.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public BankResponse createAccount(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        return userService.createAccount(createUserRequestDTO);
    }

    @GetMapping("/balanceEnquiry/{accountNumber}")
    public BankResponse balanceEnquiry(@PathVariable String accountNumber) {
        return userService.balanceEnquiry(accountNumber);
    }

    @GetMapping("/nameEnquiry/{accountNumber}")
    public String nameEnquiry(@PathVariable String accountNumber) {
        return userService.nameEnquiry(accountNumber);
    }

    @PostMapping("/credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest creditDebitRequest) {
        return userService.creditAccount(creditDebitRequest);
    }
}
