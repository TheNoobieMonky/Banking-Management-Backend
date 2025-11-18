package com.banking.BankingManagement.Repository;

import com.banking.BankingManagement.Model.Account;
import com.banking.BankingManagement.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    List<Account> findByUsers(Users users);

    Optional<Account> findByAccNumber(String accNumber);
}
