package com.smartcontrol.profile.grpc;

import java.util.Calendar;
import java.util.Date;


import com.smartcontrol.common.constant.Gender;
import com.smartcontrol.grpc.CreateProfileRequest;
import com.smartcontrol.grpc.CreateProfileResponse;
import com.smartcontrol.grpc.ProfileServieGrpc.ProfileServieImplBase;
import com.smartcontrol.profile.entity.Profile;
import com.smartcontrol.profile.repository.ProfileRepository;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@AllArgsConstructor
public class ProfileGrpcService extends ProfileServieImplBase {

    private final ProfileRepository profileRepository;

    @Override
    public void createProfile(CreateProfileRequest request, StreamObserver<CreateProfileResponse> responseObserver) {
        log.info("Received: " + request.toString());

        Profile profile = Profile.builder()
                .userId(request.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .gender(Gender.valueOf(null == request.getGender() ? "" : request.getGender()))
                .dateOfBirth(getDateOfBirth(request.getDateOfBirth()))
                .build();
        profileRepository.save(profile);

        CreateProfileResponse response = CreateProfileResponse.newBuilder()
                .setMessage("Create profile success")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private Date getDateOfBirth(com.google.type.Date googleDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, googleDate.getYear());
        calendar.set(Calendar.MONTH, googleDate.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, googleDate.getDay());

        return calendar.getTime();
    }
}
