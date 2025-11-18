package com.banking.BankingManagement.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;


@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accId;

    @Column(unique = true, nullable = false)
    private String accNumber;

    @NotBlank(message = "Account holder name cannot be blank")
    private String accHolderName;

    @PositiveOrZero(message = "Balance cannot be negative")
    private double balance;

    @NotBlank(message = "Account type cannot be blank")
    private String accType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private Users users;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    public Account() {
    }

    public Account(long accId, String accNumber, String accHolderName, double balance, String accType) {
        this.accId = accId;
        this.accNumber = accNumber;
        this.accHolderName = accHolderName;
        this.balance = balance;
        this.accType = accType;
    }

    public Account(Users users) {
        this.users = users;
    }

    public long getAccId() {
        return accId;
    }

    public void setAccId(long accId) {
        this.accId = accId;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getAccHolderName() {
        return accHolderName;
    }

    public void setAccHolderName(String accHolderName) {
        this.accHolderName = accHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
