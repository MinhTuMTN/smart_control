
package com.smartcontrol.profile.mapper;

import org.mapstruct.Mapper;

import com.smartcontrol.profile.dto.ProfileDto;
import com.smartcontrol.profile.entity.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto toProfileDto(Profile profile);
}