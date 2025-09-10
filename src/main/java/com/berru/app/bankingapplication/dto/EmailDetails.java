package com.berru.app.bankingapplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDetails {

    @NotBlank(message = "Recipient email cannot be blank")
    @Email(message = "Recipient must be a valid email address")
    private String recipient;

    @NotBlank(message = "Message body cannot be blank")
    private String messageBody;

    @NotBlank(message = "Subject cannot be blank")
    private String subject;

    private String attachment;
}
