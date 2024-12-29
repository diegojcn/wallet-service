package com.walletservice.service;

import com.walletservice.controller.domain.WalletResponse;
import com.walletservice.entity.Wallet;
import com.walletservice.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public WalletResponse createWallet(String owner) {
        Wallet wallet = new Wallet(owner);
        checkIfWalletExist(owner);
        return new WalletResponse(walletRepository.save(wallet));
    }

    private void checkIfWalletExist(String owner) {
        Optional<Wallet> wallet = walletRepository.findByOwner(owner);
        if (wallet.isPresent()) {
            throw new IllegalArgumentException("Owner user is already in use.");
        }
    }

    public BigDecimal getBalance(String owner) {
        Wallet wallet = walletRepository.findByOwner(owner)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
        return wallet.getBalance();
    }

    public WalletResponse deposit(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
        wallet.setBalance(wallet.getBalance().add(amount));
        return new WalletResponse(walletRepository.save(wallet));
    }

    public WalletResponse withdraw(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        return new WalletResponse(walletRepository.save(wallet));
    }

    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        withdraw(fromWalletId, amount);
        deposit(toWalletId, amount);
    }
}