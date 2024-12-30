package com.walletservice.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "Wallet")
@NamedNativeQuery(
        name = "findByOwner",
        query = "SELECT id,owner, balance FROM wallet where owner = :owner",
        resultClass = Wallet.class
)
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "owner")
    private String owner;

    @Column(name = "balance")
    private BigDecimal balance = BigDecimal.ZERO;

    public Wallet(){}

    public Wallet(String owner) {
        this.owner = owner;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
