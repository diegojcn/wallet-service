package com.walletservice.controller;

import com.walletservice.controller.domain.WalletResponse;
import com.walletservice.entity.TransactionType;
import com.walletservice.repository.WalletTransactionItemSummary;
import com.walletservice.repository.WalletTransactionSummary;
import com.walletservice.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/createWallet")
    public WalletResponse createWallet(@RequestParam("owner") String owner) {
        return walletService.createWallet(owner);
    }

    @GetMapping("/balance")
    public BigDecimal getBalance(@RequestParam("owner") String owner) {
        return walletService.getBalance(owner);
    }

    @GetMapping("/historical-balance")
    public WalletTransactionSummary getHistoricalBalance(@RequestParam("owner") String owner,
                                                         @RequestParam("startDate") String startDate,
                                                         @RequestParam("endDate") String endDate) {
        return walletService.getHistoricalTransaction(owner, startDate, endDate);
    }

    @PostMapping("/deposit")
    public WalletResponse deposit(@RequestParam("owner") String owner,
                                  @RequestParam("amount") BigDecimal amount) {
        return walletService.deposit(owner, amount, null, null);
    }

    @PostMapping("/withdraw")
    public WalletResponse withdraw(@RequestParam("owner") String owner,
                                   @RequestParam("amount") BigDecimal amount) {
        return walletService.withdraw(owner, amount, TransactionType.WITHDRAW, null);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestParam("fromOwner") String fromOwner,
                         @RequestParam("toOwner") String toOwner,
                         @RequestParam("amount") BigDecimal amount) {
        walletService.transfer(fromOwner, toOwner, amount);
    }
}
