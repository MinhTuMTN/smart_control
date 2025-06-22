package com.smartcontrol.gateway.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralResponse {
    private String message;
    private Object data;    
}
