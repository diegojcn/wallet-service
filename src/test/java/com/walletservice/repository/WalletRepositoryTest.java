package com.walletservice.repository;

import com.walletservice.entity.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WalletRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @BeforeEach
    public void setup() {
        walletRepository.save(new Wallet("diegojcn"));
        walletRepository.save(new Wallet("barbara"));
    }

    @Test
    public void testFindByOwner() {
        Optional<Wallet> diegojcn = walletRepository.findByOwner("diegojcn");

        assertThat(diegojcn).isNotNull();
        assertThat(diegojcn.get().getOwner()).isEqualTo("diegojcn");
    }
}
