package com.banking.BankingManagement.Controllers;

import com.banking.BankingManagement.Model.Account;
import com.banking.BankingManagement.Model.Users;
import com.banking.BankingManagement.Repository.UsersRepo;
import com.banking.BankingManagement.Services.AccountService;
import com.banking.BankingManagement.Services.CustomUserDetailsService;
import com.banking.BankingManagement.Services.JWTService;
import com.banking.BankingManagement.Services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService service;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) {
        Users createdUser = service.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Users> getUserId(@PathVariable Long userId){
        Users id = service.getUserId(userId);

        if (id != null){
            return ResponseEntity.ok(id);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<Account>> getAllAccForUser(@PathVariable Long userId){
        List<Account> acc = service.getAllAccForUser(userId);

        if (!acc.isEmpty()){
            return ResponseEntity.ok(acc);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        service.deleteUser(userId);
        return (ResponseEntity<?>) ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Users user = usersRepo.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(){

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Users user = usersRepo.findByUsername(username);

        if (user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        List<Account> accounts = service.getAllAccForUser(user.getUserId());

        if (accounts.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No account for user");
        }

        Account account = accounts.get(0);

        Map<String, Object> response = Map.of(
                "userId", user.getUserId(),
                "accId", account.getAccId()
        );

        return ResponseEntity.ok(response);
    }
}
