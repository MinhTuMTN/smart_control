package com.smartcontrol.authentication.grpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.smartcontrol.common.dto.ServiceInstanceInfo;
import com.smartcontrol.grpc.ProfileServieGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Scope("singleton")
public class ProfileGrpcFactory {

    @Autowired
    private EurekaClient eurekaClient;
    private ManagedChannel managedChannel;
    
    public ProfileServieGrpc.ProfileServieBlockingStub getProfileServiceBlockingStub() {
        try {
            ServiceInstanceInfo instanceInfo = getProfileServiceInstanceInfo();
            managedChannel = ManagedChannelBuilder
                    .forAddress(instanceInfo.getHostName(), instanceInfo.getGrpcPort())
                    .usePlaintext()
                    .build();
            return ProfileServieGrpc.newBlockingStub(managedChannel);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get EurekaClient", e);
        }
    }

    private ServiceInstanceInfo getProfileServiceInstanceInfo() {
        InstanceInfo profileInstance = eurekaClient.getNextServerFromEureka("profile-service", false);
        String profileServiceHost = profileInstance.getIPAddr();
        int grpcPort = Integer.parseInt(profileInstance.getMetadata().get("grpc-port"));
        return ServiceInstanceInfo.builder()
                .hostName(profileServiceHost)
                .port(profileInstance.getPort())
                .grpcPort(grpcPort)
                .build();
    }

    public void shutdown() {
        if (managedChannel != null) {
            managedChannel.shutdown();
        }
    }
}
