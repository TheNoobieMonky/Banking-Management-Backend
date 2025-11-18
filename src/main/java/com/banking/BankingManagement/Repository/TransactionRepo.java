package com.banking.BankingManagement.Repository;

import com.banking.BankingManagement.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository <Transaction, Long> {
    List<Transaction> findByAccountAccNumber(String accNumber);
}
