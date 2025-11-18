package com.banking.BankingManagement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;


import java.time.LocalDateTime;


@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveOrZero
    private double amount;

    @NotBlank(message = "Type cannot be blank")
    private String transactionType;
    private LocalDateTime timestamp;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @ManyToOne
    @JoinColumn(name = "acc_id", nullable = false)
    @JsonBackReference
    private Account account;

    public Transaction() {
    }

    public Transaction(Long id,
                       double amount, String transactionType, LocalDateTime timestamp, String description) {
        this.id = id;
        this.amount = amount;
        this.transactionType = transactionType;
        this.timestamp = timestamp;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
