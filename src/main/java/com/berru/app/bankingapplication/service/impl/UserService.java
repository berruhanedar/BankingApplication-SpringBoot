package com.berru.app.bankingapplication.service.impl;

import com.berru.app.bankingapplication.dto.BankResponse;
import com.berru.app.bankingapplication.dto.CreateUserRequestDTO;

public interface UserService {
    BankResponse createAccount(CreateUserRequestDTO createUserRequestDTO);

}
