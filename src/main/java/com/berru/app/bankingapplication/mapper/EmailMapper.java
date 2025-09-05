package com.berru.app.bankingapplication.mapper;

import com.berru.app.bankingapplication.dto.EmailDetails;
import com.berru.app.bankingapplication.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    @Mapping(target = "recipient", source = "email")
    @Mapping(target = "subject", constant = "ACCOUNT CREATION")
    @Mapping(target = "messageBody", expression = "java(\"Your account has been successfully created. Your Account Details:\\n\" + user.getFirstName() + \" \" + user.getLastName() + \" \" + (user.getOtherName() != null ? user.getOtherName() : \"\") + \"\\nAccount Number: \" + user.getAccountNumber())")
    EmailDetails toEmailDetails(User user);
}
