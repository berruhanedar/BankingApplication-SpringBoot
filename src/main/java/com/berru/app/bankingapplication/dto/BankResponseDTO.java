package com.berru.app.bankingapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankResponseDTO {

    private String responseMessage;

    private AccountInfo accountInfo;

    private String token;

}
