package com.berru.app.bankingapplication.mapper;

import com.berru.app.bankingapplication.dto.*;
import com.berru.app.bankingapplication.entity.User;
import com.berru.app.bankingapplication.enums.Role;
import com.berru.app.bankingapplication.utils.AccountUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", imports = {AccountUtils.class, Role.class})
public interface UserMapper {

    @Mapping(target = "accountNumber", expression = "java(AccountUtils.generateAccountNumber())")
    @Mapping(target = "accountBalance", constant = "0.0")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "role", expression = "java(Role.ROLE_USER)")
    User toEntity(CreateUserRequestDTO dto);

    UserResponseDTO toDTO(User user);

    @Mapping(target = "accountName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(target = "accountBalance", source = "user.accountBalance")
    @Mapping(target = "accountNumber", source = "user.accountNumber")
    AccountInfo toAccountInfo(User user);

    @Mapping(target = "responseMessage", constant = "Account created successfully!")
    @Mapping(target = "accountInfo", expression = "java(toAccountInfo(user))")
    BankResponseDTO toBankResponse(User user);

    @Mapping(target = "responseMessage", constant = "Account credited successfully!")
    @Mapping(target = "accountInfo", expression = "java(new AccountInfo(" +
            "user.getFirstName() + \" \" + user.getLastName(), " +
            "user.getAccountBalance(), " +
            "creditDebitRequestDTO.getAccountNumber()))")
    BankResponseDTO toCreditResponse(User user, CreditDebitRequestDTO creditDebitRequestDTO);

    @Mapping(target = "responseMessage", constant = "Transfer completed successfully!")
    @Mapping(target = "accountInfo", expression = "java(toAccountInfo(sourceAccountUser))")
    BankResponseDTO toTransferResponse(User sourceAccountUser);

    @Mapping(target = "accountNumber", source = "user.accountNumber")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "transactionType", constant = "CREDIT")
    @Mapping(target = "status", constant = "SUCCESS")
    TransactionRequestDTO toTransactionRequestDTO(User user, BigDecimal amount);

    @Mapping(target = "responseMessage", constant = "Login successful")
    @Mapping(target = "accountInfo", expression = "java(toAccountInfo(user))")
    @Mapping(target = "token", source = "jwtToken")
    BankResponseDTO toLoginResponse(User user, String jwtToken);


}