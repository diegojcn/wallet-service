package com.walletservice.service;

import com.walletservice.entity.TransactionType;
import com.walletservice.repository.WalletRepository;
import com.walletservice.repository.WalletTransactionRepository;
import com.walletservice.repository.WalletTransactionSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WalletServiceTest {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Test
    public void createWalletTest() {
        WalletService service = new WalletService(walletRepository, walletTransactionRepository);
        service.createWallet("diegojcn");

        assertThat(service.getBalance("diegojcn")).isEqualTo(new BigDecimal("0.00"));
    }


    @Test
    public void depositTest() {
        WalletService service = new WalletService(walletRepository, walletTransactionRepository);

        service.createWallet("diegojcn1");
        service.deposit("diegojcn1", new BigDecimal("500.00"), null, null);

        assertThat(service.getBalance("diegojcn1")).isEqualTo(new BigDecimal("500.00"));
    }

    @Test
    public void withdrawTest() {
        WalletService service = new WalletService(walletRepository, walletTransactionRepository);

        service.createWallet("diegojcn2");
        service.deposit("diegojcn2", new BigDecimal("500.00"), null, null);
        service.withdraw("diegojcn2", new BigDecimal("50.00"), TransactionType.WITHDRAW, null);

        assertThat(service.getBalance("diegojcn2")).isEqualTo(new BigDecimal("450.00"));
    }

    @Test
    public void transferAndHistoricalTest() {
        WalletService service = new WalletService(walletRepository, walletTransactionRepository);

        service.createWallet("diegojcn3");
        service.createWallet("barbara");

        service.deposit("diegojcn3", new BigDecimal("500.00"), null, null);
        service.transfer("diegojcn3", "barbara", new BigDecimal("450.00"));

        assertThat(service.getBalance("diegojcn3")).isEqualTo(new BigDecimal("50.00"));
        assertThat(service.getBalance("barbara")).isEqualTo(new BigDecimal("450.00"));

        WalletTransactionSummary summary = service.getHistoricalTransaction("diegojcn3",
                LocalDateTime.now().format(format),
                LocalDateTime.now().format(format));

        assertThat(summary.getTransactions().size()).isEqualTo(2);
        assertThat(summary.getCurrentBalance()).isEqualTo(new BigDecimal("50.00"));
    }

}
