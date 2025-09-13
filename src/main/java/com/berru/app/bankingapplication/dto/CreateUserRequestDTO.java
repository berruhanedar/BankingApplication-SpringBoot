package com.berru.app.bankingapplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequestDTO {

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Size(max = 50, message = "Other name cannot exceed 50 characters")
    private String otherName;

    @NotNull(message = "Gender cannot be null")
    @Size(max = 10, message = "Gender must be valid")
    private String gender;

    @NotNull(message = "Address cannot be null")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotNull(message = "State of origin cannot be null")
    @Size(max = 100, message = "State of origin cannot exceed 100 characters")
    private String stateOfOrigin;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Phone number cannot be null")
    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    private String phoneNumber;

    @Size(max = 20, message = "Alternative phone number cannot exceed 20 characters")
    private String alternativePhoneNumber;

}
