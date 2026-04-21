package com.banking.api.service;

import com.banking.api.model.Transaction;
import com.banking.api.repository.TransactionRepository;
import java.util.List;

public class TransactionService {

    private TransactionRepository transactionRepo;

    public TransactionService() { 
    	this.transactionRepo = new TransactionRepository();
    }

    public List<Transaction> getUserTransactions(String username, int page, int size) {
        // prevents negative pages or too big sizes
        if (page < 1) page = 1;
        if (size > 50) size = 50; 
        
        int offset = (page - 1) * size;
        return transactionRepo.findTransactionsByUsername(username, size, offset);
    }
}