package com.walletservice.repository;

import com.walletservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query(name = "findbyowner", nativeQuery = true)
    Optional<Wallet> findByOwner(@Param("owner") String owner);
}