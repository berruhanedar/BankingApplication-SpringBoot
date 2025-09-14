package com.berru.app.bankingapplication.service.impl;

import com.berru.app.bankingapplication.config.JwtTokenProvider;
import com.berru.app.bankingapplication.dto.*;
import com.berru.app.bankingapplication.entity.User;
import com.berru.app.bankingapplication.exception.BadRequestException;
import com.berru.app.bankingapplication.exception.DestinationAccountNotFoundException;
import com.berru.app.bankingapplication.exception.InsufficientBalanceException;
import com.berru.app.bankingapplication.exception.SourceAccountNotFoundException;
import com.berru.app.bankingapplication.mapper.EmailMapper;
import com.berru.app.bankingapplication.mapper.UserMapper;
import com.berru.app.bankingapplication.repository.TransactionRepository;
import com.berru.app.bankingapplication.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final EmailMapper emailMapper;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository,
                           EmailService emailService, EmailMapper emailMapper, TransactionService transactionService, TransactionRepository transactionRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.emailMapper = emailMapper;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public BankResponseDTO createAccount(CreateUserRequestDTO createUserRequestDTO) {
        if (userRepository.existsByEmail(createUserRequestDTO.getEmail())) {
            throw new BadRequestException("This user already has an account created!");
        }

        User newUser = userMapper.toEntity(createUserRequestDTO);

        String encodedPassword = passwordEncoder.encode(createUserRequestDTO.getPassword());
        newUser.setPassword(encodedPassword);

        User savedUser = userRepository.save(newUser);

        // EmailDetails emailDetails = emailMapper.toEmailDetails(savedUser);
        // emailService.sendEmailAlert(emailDetails);

        return userMapper.toBankResponse(savedUser);
    }

    @Override
    public BankResponseDTO balanceEnquiry(String accountNumber) {
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
    public BankResponseDTO creditAccount(CreditDebitRequestDTO creditDebitRequestDTO) {
        User userToCredit = userRepository.findByAccountNumber(creditDebitRequestDTO.getAccountNumber())
                .orElseThrow(() -> new BadRequestException("This account does not exist!"));

        userToCredit.setAccountBalance(
                userToCredit.getAccountBalance().add(creditDebitRequestDTO.getAmount())
        );

        userRepository.save(userToCredit);

        TransactionRequestDTO transactionRequestDTO = userMapper.toTransactionRequestDTO(
                userToCredit, creditDebitRequestDTO.getAmount()
        );


        return userMapper.toCreditResponse(userToCredit, creditDebitRequestDTO);
    }

    @Override
    public BankResponseDTO debitAccount(CreditDebitRequestDTO creditDebitRequestDTO) {
        User userToDebit = userRepository.findByAccountNumber(creditDebitRequestDTO.getAccountNumber())
                .orElseThrow(() -> new BadRequestException("This account does not exist!"));

        if (userToDebit.getAccountBalance().compareTo(creditDebitRequestDTO.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance!");
        }

        userToDebit.setAccountBalance(
                userToDebit.getAccountBalance().subtract(creditDebitRequestDTO.getAmount())
        );

        userRepository.save(userToDebit);

        return userMapper.toBankResponse(userToDebit);
    }

    @Transactional
    @Override
    public BankResponseDTO transfer(TransferInfoRequestDTO transferInfoRequestDTO) {
        User sourceAccountUser = userRepository.findByAccountNumber(transferInfoRequestDTO.getSourceAccountNumber())
                .orElseThrow(() -> new SourceAccountNotFoundException("Source account does not exist!"));

        User destinationAccountUser = userRepository.findByAccountNumber(transferInfoRequestDTO.getDestinationAccountNumber())
                .orElseThrow(() -> new DestinationAccountNotFoundException("Destination account does not exist!"));

        if (transferInfoRequestDTO.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
            throw new InsufficientBalanceException("Insufficient balance!");
        }

        sourceAccountUser.setAccountBalance(
                sourceAccountUser.getAccountBalance().subtract(transferInfoRequestDTO.getAmount())
        );

        destinationAccountUser.setAccountBalance(
                destinationAccountUser.getAccountBalance().add(transferInfoRequestDTO.getAmount())
        );

        userRepository.save(sourceAccountUser);
        userRepository.save(destinationAccountUser);

        TransactionRequestDTO transactionRequestDTO = userMapper.toTransactionRequestDTO(sourceAccountUser, transferInfoRequestDTO.getAmount());
        transactionService.saveTransaction(transactionRequestDTO);

        return userMapper.toTransferResponse(sourceAccountUser);
    }

    @Override
    public BankResponseDTO login(LoginRequestDTO loginRequestDTO) {
       Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(loginRequestDTO.getEmail() + " not found"));

        return userMapper.toLoginResponse(user, token);
    }
}
