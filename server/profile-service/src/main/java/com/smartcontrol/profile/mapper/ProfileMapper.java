
package com.smartcontrol.profile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.smartcontrol.common.constant.Gender;
import com.smartcontrol.common.utils.DateUtils;
import com.smartcontrol.grpc.CreateProfileRequest;
import com.smartcontrol.profile.dto.ProfileDto;
import com.smartcontrol.profile.entity.Profile;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto toProfileDto(Profile profile);
    Profile toProfile(ProfileDto profileDto);

    @Mappings({
        @Mapping(source = "gender", target = "gender"),
        @Mapping(source = "dateOfBirth", target = "dateOfBirth"),
        @Mapping(target = "phoneNumber", ignore = true)
    })
    ProfileDto toProfileDtoFromGrpc(CreateProfileRequest request);

    default Date map(com.google.type.Date googleDate) {
        return DateUtils.convertToJavaDate(googleDate);
    }

    default Gender map(String gender) {
        return Gender.valueOf(gender.toUpperCase());
    }
}