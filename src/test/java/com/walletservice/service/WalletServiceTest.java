package com.walletservice.service;

import com.walletservice.entity.TransactionType;
import com.walletservice.repository.WalletRepository;
import com.walletservice.repository.WalletTransactionRepository;
import com.walletservice.repository.WalletTransactionSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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

        assertThat(service.getBalance("diegojcn")).isEqualTo(BigDecimal.ZERO);
    }


    @Test
    public void depositTest() {
        WalletService service = new WalletService(walletRepository, walletTransactionRepository);

        service.createWallet("diegojcn");
        service.deposit("diegojcn", new BigDecimal("500"), null, null);

        assertThat(service.getBalance("diegojcn")).isEqualTo(new BigDecimal("500"));
    }

    @Test
    public void withdrawTest() {
        WalletService service = new WalletService(walletRepository, walletTransactionRepository);

        service.createWallet("diegojcn");
        service.deposit("diegojcn", new BigDecimal("500"), null, null);
        service.withdraw("diegojcn", new BigDecimal("50"), TransactionType.WITHDRAW, null);

        assertThat(service.getBalance("diegojcn")).isEqualTo(new BigDecimal("450"));
    }

    @Test
    public void transferAndHistoricalTest() {
        WalletService service = new WalletService(walletRepository, walletTransactionRepository);

        service.createWallet("diegojcn");
        service.createWallet("barbara");

        service.deposit("diegojcn", new BigDecimal("500"), null, null);
        service.transfer("diegojcn", "barbara", new BigDecimal("450"));

        assertThat(service.getBalance("diegojcn")).isEqualTo(new BigDecimal("50"));
        assertThat(service.getBalance("barbara")).isEqualTo(new BigDecimal("450"));

        WalletTransactionSummary summary = service.getHistoricalTransaction("diegojcn",
                LocalDateTime.now().format(format),
                LocalDateTime.now().format(format));

        assertThat(summary.getTransactions().size()).isEqualTo(2);
        assertThat(summary.getCurrentBalance()).isEqualTo(new BigDecimal("50.00"));
    }

}
