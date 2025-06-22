package com.smartcontrol.profile.service;

import org.springframework.stereotype.Service;

import com.smartcontrol.common.exception.SmartControlErrorCode;
import com.smartcontrol.common.exception.SmartControlException;
import com.smartcontrol.profile.dto.ProfileDto;
import com.smartcontrol.profile.mapper.ProfileMapper;
import com.smartcontrol.profile.repository.ProfileRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileDto getProfileByUserId(String userId) {
        return profileRepository.findByUserIdAndDeletedAtIsNull(userId)
                .map(profileMapper::toProfileDto)
                .orElseThrow(() -> new SmartControlException(SmartControlErrorCode.PROFILE_NOT_FOUND));
    }
}
