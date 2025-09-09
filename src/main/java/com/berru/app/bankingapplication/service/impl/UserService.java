package com.berru.app.bankingapplication.service.impl;

import com.berru.app.bankingapplication.dto.BankResponse;
import com.berru.app.bankingapplication.dto.CreateUserRequestDTO;
import com.berru.app.bankingapplication.dto.CreditDebitRequest;
import com.berru.app.bankingapplication.dto.TransferInfo;

public interface UserService {
    BankResponse createAccount(CreateUserRequestDTO createUserRequestDTO);
    BankResponse balanceEnquiry(String accountNumber);
    String nameEnquiry(String accountNumber);
    BankResponse creditAccount(CreditDebitRequest  creditDebitRequest);
    BankResponse debitAccount(CreditDebitRequest creditDebitRequest);
    BankResponse transfer(TransferInfo transferInfo);
}
