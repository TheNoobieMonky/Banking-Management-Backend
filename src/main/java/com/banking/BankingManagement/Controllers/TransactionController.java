package com.banking.BankingManagement.Controllers;

import com.banking.BankingManagement.Config.SecurityUtils;
import com.banking.BankingManagement.Model.Account;
import com.banking.BankingManagement.Model.Transaction;
import com.banking.BankingManagement.Repository.AccountRepo;
import com.banking.BankingManagement.Services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/{accNumber}/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountRepo accountRepo;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@PathVariable String accNumber,
                                                         @Valid @RequestBody Transaction transaction){

        Account account = accountRepo.findByAccNumber(accNumber)
                .orElseThrow( () -> new IllegalArgumentException("Account Not Found"));

        String currentUsername = SecurityUtils.getCurrentUsername();
        if (!account.getUsers().getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You don't have permission to access this account");
        }

        transaction.setAccount(account);

        Transaction savedTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactionByAccNo(@PathVariable String accNumber){

        Account account = accountRepo.findByAccNumber(accNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        String currentUsername = SecurityUtils.getCurrentUsername();
        if (!account.getUsers().getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You don't have permission to access this account");
        }

        List<Transaction> transactions = transactionService.getTransactionByAccNo(accNumber);

        if (transactions.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return ResponseEntity.ok(transactions);
        }
    }


}
