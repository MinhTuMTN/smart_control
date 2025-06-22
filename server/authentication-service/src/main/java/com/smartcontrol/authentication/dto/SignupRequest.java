package com.smartcontrol.authentication.dto;

import java.util.Date;

import com.smartcontrol.common.constant.Gender;
import com.smartcontrol.common.validator.DateOfBirth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Confirm password is required")
    @Size(min = 8, message = "Confirm password must be at least 8 characters long")
    private String confirmPassword;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Gender is required. Valid values: [MALE, FEMALE, OTHERS]")
    private Gender gender;

    @DateOfBirth
    @NotNull(message = "Date of birth is required")
    private Date dateOfBirth;
}
