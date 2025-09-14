package com.berru.app.bankingapplication.service.impl;

import com.berru.app.bankingapplication.dto.*;

public interface UserService {
    BankResponseDTO createAccount(CreateUserRequestDTO createUserRequestDTO);
    BankResponseDTO balanceEnquiry(String accountNumber);
    String nameEnquiry(String accountNumber);
    BankResponseDTO creditAccount(CreditDebitRequestDTO creditDebitRequestDTO);
    BankResponseDTO debitAccount(CreditDebitRequestDTO creditDebitRequestDTO);
    BankResponseDTO transfer(TransferInfoRequestDTO transferInfoRequestDTO);
    BankResponseDTO login(LoginRequestDTO loginRequestDTO);
}
