package com.walletservice.controller;

import com.walletservice.controller.domain.WalletResponse;
import com.walletservice.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/teste")
    public String getTest() {
        return "Teste";
    }

    @PostMapping("/createWallet")
    public WalletResponse createWallet(@RequestParam("owner") String owner) {
        return walletService.createWallet(owner);
    }

    @GetMapping("/balance")
    public BigDecimal getBalance(@RequestParam("owner") String owner) {
        return walletService.getBalance(owner);
    }

    @PostMapping("deposit")
    public WalletResponse deposit(@RequestParam("walletId") Long walletId,
                                  @RequestParam("amount") BigDecimal amount) {
        return walletService.deposit(walletId, amount);
    }

    @PostMapping("/withdraw")
    public WalletResponse withdraw(@RequestParam("walletId") Long walletId,
                                   @RequestParam("amount") BigDecimal amount) {
        return walletService.withdraw(walletId, amount);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestParam("fromWalletId") Long fromWalletId,
                         @RequestParam("toWalletId") Long toWalletId,
                         @RequestParam("amount") BigDecimal amount) {
        walletService.transfer(fromWalletId, toWalletId, amount);
    }
}
