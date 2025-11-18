package com.banking.BankingManagement.Services;

import com.banking.BankingManagement.Model.Account;
import com.banking.BankingManagement.Model.Transaction;
import com.banking.BankingManagement.Model.Users;
import com.banking.BankingManagement.Repository.TransactionRepo;
import com.banking.BankingManagement.Repository.AccountRepo;
import com.banking.BankingManagement.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accRepo;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private UsersRepo usersRepo;

    private String generateAccNo(){
        return "ACC" + (int)(Math.random() * 100000000);
    }

    public Account createAcc(Account account, Long userId) {

        Users user;

        if (userId != null) {
            user = usersRepo.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        } else if (account.getUsers() != null) {
            user = usersRepo.save(account.getUsers());
        } else {
            throw new IllegalArgumentException("User information is required");
        }

        account.setUsers(user);
        account.setAccNumber(generateAccNo());

        if (account.getAccHolderName() == null || account.getAccHolderName().isBlank()) {
            throw new IllegalArgumentException("Account holder name cannot be empty");
        }
        if (account.getAccType() == null || account.getAccType().isBlank()) {
            throw new IllegalArgumentException("Account type must be specified");
        }
        if (account.getBalance() < 500) {
            throw new IllegalArgumentException("Minimum opening balance is 500");
        }

        return accRepo.save(account);
    }

    public Account getAccById(Long id) {
        return accRepo.findById(id).orElse(null);
    }

    public Account deposit(Long id, double amount) {
        Account acc = getAccById(id);

        if (acc == null){
            return null;
        }
        acc.setBalance(acc.getBalance() + amount);
        accRepo.save(acc);

        Transaction transaction = new Transaction();
        transaction.setAccount(acc);
        transaction.setAmount(amount);
        transaction.setTransactionType("Credit");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription("Credited to account");
        transactionRepo.save(transaction);

        return acc;
    }

    public Account withdraw(Long id, double amount) {
        Account acc = getAccById(id);

        if (acc == null || acc.getBalance() < amount){
            return null;
        }
        acc.setBalance(acc.getBalance() - amount);
        accRepo.save(acc);

        Transaction transaction = new Transaction();
        transaction.setAccount(acc);
        transaction.setAmount(amount);
        transaction.setTransactionType("Debit");
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription("Debited from account");
        transactionRepo.save(transaction);

        return acc;
    }

    public Double getBalance(Long id) {
        Account acc = getAccById(id);

        if (acc != null){
            return acc.getBalance();
        }
        return null;
    }

    public void deleteAcc(Long id) {
        if (accRepo.existsById(id)){
            accRepo.deleteById(id);

        }
    }
}
