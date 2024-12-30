package com.walletservice.entity;

import com.walletservice.repository.WalletTransactionItemSummary;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "wallet_transaction")
@NamedNativeQuery(
        name = "transactionsByDate",
        query = "SELECT balance, transaction_value AS transactionValue, transaction_date AS transactionDate, type " +
                "FROM wallet_transaction where wallet = :wallet " +
                "AND transaction_date BETWEEN :startDate AND :endDate " +
                "ORDER BY transaction_date DESC",
        resultSetMapping = "WalletTransactionSummary"
)
@SqlResultSetMapping(
        name = "WalletTransactionSummary",
        classes = @ConstructorResult(
                targetClass = WalletTransactionItemSummary.class,
                columns = {
                        @ColumnResult(name = "balance", type = BigDecimal.class),
                        @ColumnResult(name = "type", type = String.class),
                        @ColumnResult(name = "transactionValue", type = BigDecimal.class),
                        @ColumnResult(name = "transactionDate", type = LocalDateTime.class)
                }
        )
)
public class WalletTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "wallet")
    private Wallet wallet;

    @Column(name = "type")
    private String type;

    @Column(name = "transactionValue")
    private BigDecimal transactionValue;

    @ManyToOne
    @JoinColumn(name = "walletFrom")
    private Wallet walletFrom;

    @ManyToOne
    @JoinColumn(name = "walletTo")
    private Wallet walletTo;

    @Column(name = "transactionDate")
    private LocalDateTime transactionDate;

    @Column(name = "balance")
    private BigDecimal balance;

    public WalletTransaction(String type, BigDecimal transactionValue, Wallet wallet, Wallet walletTo, Wallet walletFrom, LocalDateTime transactionDate, BigDecimal balance) {
        this.type = type;
        this.transactionValue = transactionValue;
        this.wallet = wallet;
        this.walletTo = walletTo;
        this.walletFrom = walletFrom;
        this.transactionDate = transactionDate;
        this.balance = balance;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getTransactionValue() {
        return transactionValue;
    }

    public Wallet getWalletFrom() {
        return walletFrom;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTransactionValue(BigDecimal transactionValue) {
        this.transactionValue = transactionValue;
    }

    public void setWalletFrom(Wallet walletFrom) {
        this.walletFrom = walletFrom;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Wallet getWalletTo() {
        return walletTo;
    }

    public void setWalletTo(Wallet walletTo) {
        this.walletTo = walletTo;
    }
}
