package com.walletservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@NamedNativeQuery(
        name = "findbyowner",
        query =
                "SELECT   * " +
                        "FROM Wallet " +
                        "where owner = :owner ",
        resultClass = Wallet.class
)
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String owner;

    private BigDecimal balance = BigDecimal.ZERO;

    public Wallet(){}

    public Wallet(String owner) {
        this.owner = owner;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
