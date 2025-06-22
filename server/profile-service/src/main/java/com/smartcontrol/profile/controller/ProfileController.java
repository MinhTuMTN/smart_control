package com.smartcontrol.profile.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontrol.common.dto.GeneralResponse;
import com.smartcontrol.profile.service.ProfileService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public GeneralResponse getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return GeneralResponse.builder()
                .message("Get profile successfully")
                .data(profileService.getProfileByUserId(authentication.getName()))
                .build();
    }
}
