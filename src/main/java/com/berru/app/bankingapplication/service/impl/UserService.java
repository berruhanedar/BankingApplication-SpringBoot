package com.berru.app.bankingapplication.service.impl;

import com.berru.app.bankingapplication.dto.BankResponseDTO;
import com.berru.app.bankingapplication.dto.CreateUserRequestDTO;
import com.berru.app.bankingapplication.dto.CreditDebitRequestDTO;
import com.berru.app.bankingapplication.dto.TransferInfo;

public interface UserService {
    BankResponseDTO createAccount(CreateUserRequestDTO createUserRequestDTO);
    BankResponseDTO balanceEnquiry(String accountNumber);
    String nameEnquiry(String accountNumber);
    BankResponseDTO creditAccount(CreditDebitRequestDTO creditDebitRequestDTO);
    BankResponseDTO debitAccount(CreditDebitRequestDTO creditDebitRequestDTO);
    BankResponseDTO transfer(TransferInfo transferInfo);
}
