package com.berru.app.bankingapplication.mapper;

import com.berru.app.bankingapplication.dto.TransactionRequestDTO;
import com.berru.app.bankingapplication.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toEntity(TransactionRequestDTO dto);

    TransactionRequestDTO toDTO(Transaction entity);
}
