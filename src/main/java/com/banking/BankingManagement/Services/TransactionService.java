package com.banking.BankingManagement.Services;


import com.banking.BankingManagement.Model.Account;
import com.banking.BankingManagement.Model.Transaction;
import com.banking.BankingManagement.Repository.AccountRepo;
import com.banking.BankingManagement.Repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepo transactionRepo;
    @Autowired
    AccountRepo accountRepo;

    public Transaction createTransaction(Transaction transaction) {

        if (transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero");
        }

        if (transaction.getTransactionType() == null || transaction.getTransactionType().isBlank()) {
            throw new IllegalArgumentException("Transaction type must be specified (Credit/Debit)");
        }

        if (transaction.getDescription() == null || transaction.getDescription().isBlank()) {
            transaction.setDescription("No description provided");
        }

        transaction.setTimestamp(LocalDateTime.now());
        return transactionRepo.save(transaction);
    }

    public List<Transaction> getTransactionByAccNo(String accNumber) {
        return transactionRepo.findByAccountAccNumber(accNumber);
    }
}
