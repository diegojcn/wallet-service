package com.walletservice.service;

import com.walletservice.controller.domain.WalletResponse;
import com.walletservice.entity.WalletTransaction;
import com.walletservice.entity.TransactionType;
import com.walletservice.entity.Wallet;
import com.walletservice.repository.WalletRepository;
import com.walletservice.repository.WalletTransactionRepository;
import com.walletservice.repository.WalletTransactionWrapped;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    private final WalletTransactionRepository walletTransactionRepository;

    public WalletService(WalletRepository walletRepository, WalletTransactionRepository walletTransactionRepository) {
        this.walletRepository = walletRepository;
        this.walletTransactionRepository = walletTransactionRepository;
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
        Wallet wallet = getWalletByOwner(owner);
        return wallet.getBalance();
    }

    public WalletResponse deposit(String owner, BigDecimal amount, Wallet walletTo, Wallet walletFrom) {
        Wallet wallet = getWalletByOwner(owner);
        wallet.setBalance(wallet.getBalance().add(amount));

        saveTransaction(amount, wallet, walletTo, walletFrom, TransactionType.DEPOSIT);

        return new WalletResponse(walletRepository.save(wallet));
    }

    private void saveTransaction(BigDecimal amount, Wallet walletOwner, Wallet walletTo, Wallet walletFrom, TransactionType transactionType) {
        walletTransactionRepository.save(
                new WalletTransaction(transactionType.name(), amount, walletOwner, walletTo, walletFrom, LocalDateTime.now(),
                        walletOwner.getBalance()));
    }

    private Wallet getWalletByOwner(String owner) {
        return walletRepository.findByOwner(owner)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
    }

    public WalletResponse withdraw(String owner, BigDecimal amount, TransactionType transactionType, Wallet walletTo) {
        Wallet wallet = getWalletByOwner(owner);
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));

        saveTransaction(amount, wallet, walletTo, null, transactionType);
        return new WalletResponse(walletRepository.save(wallet));
    }

    public void transfer(String ownerFrom, String ownerTo, BigDecimal amount) {
        Wallet walletFrom = getWalletByOwner(ownerFrom);
        Wallet walletTo = getWalletByOwner(ownerTo);
        withdraw(ownerFrom, amount, TransactionType.TRANSFER, walletTo);
        deposit(ownerTo, amount, null, walletFrom);
    }


    public List<WalletTransactionWrapped> getHistoricalTransaction(String owner, String startDate, String endDate) {
        Wallet wallet = getWalletByOwner(owner);

        return walletTransactionRepository.getTransactionsByDate(
                        wallet.getId(),
                        startDate.concat("T00:00:00.00"),
                        endDate.concat("T23:59:59.999"))
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
    }

}