package com.smartcontrol.authentication.grpc;

import org.springframework.stereotype.Service;

import com.smartcontrol.authentication.dto.SignupRequest;
import com.smartcontrol.common.utils.DateUtils;
import com.smartcontrol.grpc.CreateProfileRequest;
import com.smartcontrol.grpc.CreateProfileResponse;
import com.smartcontrol.grpc.ProfileServieGrpc.ProfileServieBlockingStub;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileGrpcServiceClient {

    private final ProfileGrpcFactory profileGrpcFactory;

    public CreateProfileResponse createProfile(String userId, SignupRequest signupRequest) {
        try {
            CreateProfileRequest request = CreateProfileRequest.newBuilder()
                    .setEmail(signupRequest.getEmail())
                    .setFirstName(signupRequest.getFirstName())
                    .setLastName(signupRequest.getLastName())
                    .setGender(signupRequest.getGender().name())
                    .setUserId(userId)
                    .setDateOfBirth(DateUtils.convertToGoogleDate(signupRequest.getDateOfBirth()))
                    .build();
            ProfileServieBlockingStub blockingStub = profileGrpcFactory.getProfileServiceBlockingStub();
            return blockingStub.createProfile(request);
        } finally {
            profileGrpcFactory.shutdown();
        }
    }
}
