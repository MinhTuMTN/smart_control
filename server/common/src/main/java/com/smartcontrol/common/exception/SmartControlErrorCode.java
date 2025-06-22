package com.smartcontrol.common.exception;

public enum SmartControlErrorCode {
    
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "Invalid email or password"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "User already exists"),
    INVALID_CONFIRM_PASSWORD("INVALID_CONFIRM_PASSWORD", "Confirm password does not match password"),
    TOKEN_EXPIRED("TOKEN_EXPIRED", "Token has expired"),
    TOKEN_INVALID("TOKEN_INVALID", "Token is invalid"),
    PROFILE_NOT_FOUND("PROFILE_NOT_FOUND", "Profile not found for the given user ID"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal server error");

    private final String code;
    private final String message;

    SmartControlErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
