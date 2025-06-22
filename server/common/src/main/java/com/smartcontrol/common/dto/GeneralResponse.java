package com.smartcontrol.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GeneralResponse {
    @Builder.Default
    private String responseCode = "SUCCESS";
    private String message;
    private Object data;
    private Object errors;

    public GeneralResponse() {
        this.responseCode = "SUCCESS";
    }

    public static GeneralResponseBuilder builderWithDefaults() {
        return GeneralResponse.builder().responseCode("SUCCESS");
    }
}
