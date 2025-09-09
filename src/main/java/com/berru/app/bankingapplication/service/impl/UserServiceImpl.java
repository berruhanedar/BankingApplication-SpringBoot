package com.berru.app.bankingapplication.service.impl;

import com.berru.app.bankingapplication.dto.*;
import com.berru.app.bankingapplication.entity.User;
import com.berru.app.bankingapplication.exception.BadRequestException;
import com.berru.app.bankingapplication.exception.DestinationAccountNotFoundException;
import com.berru.app.bankingapplication.exception.InsufficientBalanceException;
import com.berru.app.bankingapplication.exception.SourceAccountNotFoundException;
import com.berru.app.bankingapplication.mapper.EmailMapper;
import com.berru.app.bankingapplication.mapper.UserMapper;
import com.berru.app.bankingapplication.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final EmailMapper emailMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository,
                           EmailService emailService, EmailMapper emailMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.emailMapper = emailMapper;
    }

    @Override
    public BankResponse createAccount(CreateUserRequestDTO createUserRequestDTO) {
        if (userRepository.existsByEmail(createUserRequestDTO.getEmail())) {
            throw new BadRequestException("This user already has an account created!");
        }

        User newUser = userMapper.toEntity(createUserRequestDTO);
        User savedUser = userRepository.save(newUser);

        EmailDetails emailDetails = emailMapper.toEmailDetails(savedUser);
        emailService.sendEmailAlert(emailDetails);

        return userMapper.toBankResponse(savedUser);
    }

    @Override
    public BankResponse balanceEnquiry(String accountNumber) {
        User foundUser = userRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BadRequestException("This account does not exist!"));

        return userMapper.toBankResponse(foundUser);
    }

    @Override
    public String nameEnquiry(String accountNumber) {
        User foundUser = userRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BadRequestException("This account does not exist!"));

        return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest creditDebitRequest) {
        User userToCredit = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber())
                .orElseThrow(() -> new BadRequestException("This account does not exist!"));

        userToCredit.setAccountBalance(
                userToCredit.getAccountBalance().add(creditDebitRequest.getAmount())
        );

        userRepository.save(userToCredit);

        return userMapper.toCreditResponse(userToCredit, creditDebitRequest);
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        User userToDebit = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber())
                .orElseThrow(() -> new BadRequestException("This account does not exist!"));

        if (userToDebit.getAccountBalance().compareTo(creditDebitRequest.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance!");
        }

        userToDebit.setAccountBalance(
                userToDebit.getAccountBalance().subtract(creditDebitRequest.getAmount())
        );

        userRepository.save(userToDebit);

        return userMapper.toBankResponse(userToDebit);
    }

    @Transactional
    @Override
    public BankResponse transfer(TransferInfo transferInfo) {
        User sourceAccountUser = userRepository.findByAccountNumber(transferInfo.getSourceAccountNumber())
                .orElseThrow(() -> new SourceAccountNotFoundException("Source account does not exist!"));

        User destinationAccountUser = userRepository.findByAccountNumber(transferInfo.getDestinationAccountNumber())
                .orElseThrow(() -> new DestinationAccountNotFoundException("Destination account does not exist!"));

        if (transferInfo.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
            throw new InsufficientBalanceException("Insufficient balance!");
        }

        sourceAccountUser.setAccountBalance(
                sourceAccountUser.getAccountBalance().subtract(transferInfo.getAmount())
        );

        destinationAccountUser.setAccountBalance(
                destinationAccountUser.getAccountBalance().add(transferInfo.getAmount())
        );

        userRepository.save(sourceAccountUser);
        userRepository.save(destinationAccountUser);

        return userMapper.toTransferResponse(sourceAccountUser);
    }
}
