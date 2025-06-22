package com.smartcontrol.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcontrol.authentication.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByEmailAndDeletedAtIsNull(String email);
}
