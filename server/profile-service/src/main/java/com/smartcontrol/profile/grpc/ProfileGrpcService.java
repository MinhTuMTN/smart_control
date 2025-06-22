package com.smartcontrol.profile.grpc;

import com.smartcontrol.grpc.CreateProfileRequest;
import com.smartcontrol.grpc.CreateProfileResponse;
import com.smartcontrol.grpc.ProfileServieGrpc.ProfileServieImplBase;
import com.smartcontrol.profile.dto.ProfileDto;
import com.smartcontrol.profile.mapper.ProfileMapper;
import com.smartcontrol.profile.service.ProfileService;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@AllArgsConstructor
public class ProfileGrpcService extends ProfileServieImplBase {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @Override
    public void createProfile(CreateProfileRequest request, StreamObserver<CreateProfileResponse> responseObserver) {
        log.info("Received: " + request.toString());

        ProfileDto profileDto = profileMapper.toProfileDtoFromGrpc(request);
        profileService.createProfile(profileDto);
        CreateProfileResponse response = CreateProfileResponse.newBuilder()
                .setMessage("Create profile success")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
