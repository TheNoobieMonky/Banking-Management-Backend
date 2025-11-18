package com.banking.BankingManagement.Services;

import com.banking.BankingManagement.Model.Account;
import com.banking.BankingManagement.Model.Users;
import com.banking.BankingManagement.Repository.AccountRepo;
import com.banking.BankingManagement.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    UsersRepo userRepo;

    @Autowired
    AccountRepo accountRepo;

    public Users createUser(Users user) {

        if (user.getFullName() == null || user.getFullName().isBlank()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        return userRepo.save(user);
    }

    public Users getUserId(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);
    }

    public List<Account> getAllAccForUser(Long userId) {

        Users user = userRepo.findById(userId).orElse(null);

        if (user != null){
            return accountRepo.findByUsers(user);
        }
        return List.of();
    }


}
