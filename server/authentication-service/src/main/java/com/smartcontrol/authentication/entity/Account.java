package com.smartcontrol.authentication.entity;

import com.smartcontrol.common.constant.Role;
import com.smartcontrol.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String userId;

    private String email;
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
}
