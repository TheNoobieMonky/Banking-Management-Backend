package com.banking.BankingManagement.Controllers;

import com.banking.BankingManagement.Model.Account;
import com.banking.BankingManagement.Services.AccountService;
import com.banking.BankingManagement.Services.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService service;
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Account> createAcc( @Valid @RequestBody Account account,
                                              @RequestParam(required = false) Long userId){
        Account createdAcc = service.createAcc(account, userId);
        return new ResponseEntity<>(createdAcc, HttpStatus.CREATED);
    }

    @GetMapping("/{accId}")
    public ResponseEntity<Account> getAccById(@PathVariable("accId") Long id){

        Account acc = service.getAccById(id);

        if (acc != null){
            return ResponseEntity.ok(acc);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{accId}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable("accId") Long id,
                                   @RequestParam @Positive(message = "Amount must be positive") double amount){

        Account updateAcc = service.deposit(id, amount);

        if (updateAcc != null){
            return ResponseEntity.ok(updateAcc);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{accId}/withdraw")
    public ResponseEntity<Account> withdrawMoney(@PathVariable("accId") Long id,
                                     @RequestParam @Positive(message = "Amount must be positive") double amount){

        Account updateAcc = service.withdraw(id, amount);

        if (updateAcc != null){
            return ResponseEntity.ok(updateAcc);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{accId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable("accId") Long id){

        Double balance = service.getBalance(id);

        if (balance != null){
            return ResponseEntity.ok(balance);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{accId}")
    public ResponseEntity<?> deleteAcc(@PathVariable Long id){
        service.deleteAcc(id);
        return (ResponseEntity<?>) ResponseEntity.noContent().build();

    }
}
