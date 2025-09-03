package com.berru.app.bankingapplication.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequestDTO {

    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @Size(max = 50, message = "Other name cannot exceed 50 characters")
    private String otherName;

    @Size(max = 10, message = "Gender cannot exceed 10 characters")
    private String gender;

    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @Size(max = 50, message = "State of origin cannot exceed 50 characters")
    private String stateOfOrigin;

    @Size(max = 20, message = "Account number cannot exceed 20 characters")
    private String accountNumber;

    private String status;

    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    private String phoneNumber;

    @Size(max = 20, message = "Alternative phone number cannot exceed 20 characters")
    private String alternativePhoneNumber;

}
