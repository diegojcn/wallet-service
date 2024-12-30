package com.walletservice.repository;

import com.walletservice.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    @Query(name = "transactionsByDate", nativeQuery = true)
    Optional<List<WalletTransactionItemSummary>> getTransactionsByDate(@Param("wallet") String wallet,
                                                                       @Param("startDate") String startDate,
                                                                       @Param("endDate") String endDate);

}