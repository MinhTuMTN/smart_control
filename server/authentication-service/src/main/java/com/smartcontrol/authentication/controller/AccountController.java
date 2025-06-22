package com.smartcontrol.authentication.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontrol.authentication.dto.AuthenticationResponse;
import com.smartcontrol.authentication.dto.LoginRequest;
import com.smartcontrol.authentication.dto.SignupRequest;
import com.smartcontrol.authentication.service.AccountService;
import com.smartcontrol.common.dto.GeneralResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signup")
    public GeneralResponse signupAccount(@RequestBody @Valid SignupRequest signupRequest) {
        AuthenticationResponse authenticationResponse = accountService.signupAccount(signupRequest);
        return GeneralResponse.builder()
                .message("Signup successful")
                .data(authenticationResponse)
                .build();
    }

    @PostMapping("/login")
    public GeneralResponse loginAccount(@RequestBody @Valid LoginRequest loginRequest) {
        AuthenticationResponse authenticationResponse = accountService.loginAccount(loginRequest);
        return GeneralResponse.builder()
                .message("Login successful")
                .data(authenticationResponse)
                .build();
    }
}
