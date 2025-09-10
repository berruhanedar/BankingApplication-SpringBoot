package com.berru.app.bankingapplication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

    @NotNull(message = "Transaction type cannot be null")
    @Pattern(regexp = "CREDIT|DEBIT", message = "Transaction type must be CREDIT or DEBIT")
    private String transactionType;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Account number cannot be null")
    @Pattern(regexp = "\\d{10}", message = "Account number must be 10 digits")
    private String accountNumber;

    @NotNull(message = "Status cannot be null")
    @Pattern(regexp = "SUCCESS|FAILED", message = "Status must be SUCCESS or FAILED")
    private String status;
}
