package com.smartcontrol.authentication.service;

import static com.smartcontrol.common.exception.SmartControlErrorCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smartcontrol.authentication.dto.AuthenticationResponse;
import com.smartcontrol.authentication.dto.LoginRequest;
import com.smartcontrol.authentication.dto.SignupRequest;
import com.smartcontrol.authentication.entity.Account;
import com.smartcontrol.common.exception.SmartControlErrorCode;
import com.smartcontrol.common.exception.SmartControlException;
import com.smartcontrol.authentication.grpc.ProfileGrpcServiceClient;
import com.smartcontrol.authentication.repository.AccountRepository;
import com.smartcontrol.common.constant.Role;
import com.smartcontrol.common.dto.Token;
import com.smartcontrol.common.jwt.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final JwtProvider jwtProvider;
    private final ProfileGrpcServiceClient profileGrpcServiceClient;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public AuthenticationResponse signupAccount(SignupRequest signupRequest) {
        // Check if email already exists
        if (accountRepository.findByEmailAndDeletedAtIsNull(signupRequest.getEmail()).isPresent()) {
            throw new SmartControlException(SmartControlErrorCode.USER_ALREADY_EXISTS);
        }

        // Check if password is valid
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            throw new SmartControlException(SmartControlErrorCode.INVALID_CONFIRM_PASSWORD);
        }
        
        Account account = new Account();
        account.setEmail(signupRequest.getEmail());
        account.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        account.setRole(Role.USER);

        Account savedAccount = accountRepository.save(account);
        Token accessToken = jwtProvider.generateAccessToken(savedAccount.getUserId(), savedAccount.getRole());
        Token refreshToken = jwtProvider.generateRefreshToken(savedAccount.getUserId(), savedAccount.getRole());

        profileGrpcServiceClient.createProfile(savedAccount.getUserId(),signupRequest);

        return AuthenticationResponse.builder()
            .userId(savedAccount.getUserId())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    public AuthenticationResponse loginAccount(LoginRequest request) {
        Account account = accountRepository.findByEmailAndDeletedAtIsNull(request.getEmail())
            .orElseThrow(() -> new SmartControlException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new SmartControlException(INVALID_CREDENTIALS);
        }

        Token accessToken = jwtProvider.generateAccessToken(account.getUserId(), account.getRole());
        Token refreshToken = jwtProvider.generateRefreshToken(account.getUserId(), account.getRole());

        return AuthenticationResponse.builder()
            .userId(account.getUserId())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
