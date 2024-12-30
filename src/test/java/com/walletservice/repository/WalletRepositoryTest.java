package com.walletservice.repository;

import com.walletservice.entity.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Test
    public void testFindByOwner() {
        walletRepository.save(new Wallet("diegojcn0"));
        Optional<Wallet> diegojcn = walletRepository.findByOwner("diegojcn0");

        assertThat(diegojcn).isNotNull();
        assertThat(diegojcn.get().getOwner()).isEqualTo("diegojcn0");
    }
}
