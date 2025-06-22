package com.smartcontrol.common.exception;

import lombok.Getter;

@Getter
public class SmartControlException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public SmartControlException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SmartControlException(SmartControlErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
    }
}
