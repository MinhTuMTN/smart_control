package com.smartcontrol.profile.service;

import org.springframework.stereotype.Service;

import com.smartcontrol.common.exception.SmartControlErrorCode;
import com.smartcontrol.common.exception.SmartControlException;
import com.smartcontrol.profile.dto.ProfileDto;
import com.smartcontrol.profile.entity.Profile;
import com.smartcontrol.profile.mapper.ProfileMapper;
import com.smartcontrol.profile.repository.ProfileRepository;

import jakarta.transaction.Transactional;
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

    @Transactional(rollbackOn = Exception.class)
    public ProfileDto createProfile(ProfileDto profileDto) {
        Profile profile = profileMapper.toProfile(profileDto);
        
        // Save the profile entity
        Profile savedProfile = profileRepository.save(profile);
        
        // Convert back to DTO
        return profileMapper.toProfileDto(savedProfile);
    }
}
