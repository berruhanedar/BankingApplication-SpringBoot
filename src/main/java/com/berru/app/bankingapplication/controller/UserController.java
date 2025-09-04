package com.berru.app.bankingapplication.controller;

import com.berru.app.bankingapplication.dto.BankResponse;
import com.berru.app.bankingapplication.dto.CreateUserRequestDTO;
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
}
