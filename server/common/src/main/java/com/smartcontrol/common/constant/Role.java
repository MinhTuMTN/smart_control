package com.smartcontrol.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"), // Administrator with full access
    USER("ROLE_USER"); // Regular user with limited access

    private String value;
}
