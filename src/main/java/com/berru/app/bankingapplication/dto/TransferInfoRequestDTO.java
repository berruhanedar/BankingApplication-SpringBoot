package com.berru.app.bankingapplication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferInfoRequestDTO {

    @NotNull(message = "Source account number cannot be null")
    @Pattern(regexp = "\\d{10}", message = "Source account number must be 10 digits")
    private String sourceAccountNumber;

    @NotNull(message = "Destination account number cannot be null")
    @Pattern(regexp = "\\d{10}", message = "Destination account number must be 10 digits")
    private String destinationAccountNumber;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than 0")
    private BigDecimal amount;
}
