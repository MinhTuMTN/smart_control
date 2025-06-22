package com.smartcontrol.authentication.dto;

import com.smartcontrol.common.dto.Token;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationResponse {
    private String userId;
    private Token accessToken;
    private Token refreshToken;
}
