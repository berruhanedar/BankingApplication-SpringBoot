package com.berru.app.bankingapplication.service.impl;

import com.berru.app.bankingapplication.dto.BankResponse;
import com.berru.app.bankingapplication.dto.CreateUserRequestDTO;
import com.berru.app.bankingapplication.dto.EmailDetails;
import com.berru.app.bankingapplication.entity.User;
import com.berru.app.bankingapplication.exception.BadRequestException;
import com.berru.app.bankingapplication.mapper.EmailMapper;
import com.berru.app.bankingapplication.mapper.UserMapper;
import com.berru.app.bankingapplication.repository.UserRepository;
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
}
