package com.smartcontrol.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.smartcontrol.common.dto.GeneralResponse;

@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * Handles SmartControlException and returns a standardized error response.
     *
     * @param ex the SmartControlException to handle
     * @return a GeneralResponse containing the error response
     */
    @ExceptionHandler(SmartControlException.class)
    public GeneralResponse handleSmartControlException(SmartControlException ex) {
        return GeneralResponse.builder()
                .message(ex.getMessage())
                .responseCode(ex.getErrorCode())
                .build();
    }

    /**
     * Handles MethodArgumentNotValidException and returns a response with
     * validation errors.
     *
     * @param ex the MethodArgumentNotValidException to handle
     * @return a GeneralResponse containing the validation error response
     */
    @ExceptionHandler
    public GeneralResponse handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getAllErrors().stream().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return GeneralResponse.builder()
                .message("Invalid input data")
                .responseCode("INVALID_INPUT")
                .errors(errors)
                .build();
    }

    /**
     * Handles all other exceptions and returns a generic error response.
     *
     * @param ex the Exception to handle
     * @return a GeneralResponse containing the generic error response
     */
    @ExceptionHandler(Exception.class)
    public GeneralResponse handleException(Exception ex) {
        return GeneralResponse.builder()
                .responseCode("INTERNAL_SERVER_ERROR")
                .message("Internal Server Error")
                .build();
    }
}
