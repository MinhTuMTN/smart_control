package com.smartcontrol.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcontrol.profile.entity.Profile;


@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    
    Optional<Profile> findByUserIdAndDeletedAtIsNull(String userId);
}
