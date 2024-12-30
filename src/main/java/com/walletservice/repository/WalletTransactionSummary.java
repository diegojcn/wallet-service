package com.walletservice.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class WalletTransactionSummary {

    private BigDecimal currentBalance = BigDecimal.ZERO;

    private List<WalletTransactionItemSummary> transactions;

    public WalletTransactionSummary(List<WalletTransactionItemSummary> transactions) {
        if (!transactions.isEmpty()) {
            this.currentBalance = transactions.getFirst().getBalance();
        }
        this.transactions = transactions;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public List<WalletTransactionItemSummary> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<WalletTransactionItemSummary> transactions) {
        this.transactions = transactions;
    }
}
