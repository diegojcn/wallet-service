package com.walletservice.controller.domain;

import com.walletservice.entity.Wallet;

import java.math.BigDecimal;

public class WalletResponse {

    private String owner;

    private BigDecimal balance = BigDecimal.ZERO;

    public WalletResponse(Wallet wallet) {
        this.balance = wallet.getBalance();
        this.owner = wallet.getOwner();
    }

    public String getOwner() {
        return owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
