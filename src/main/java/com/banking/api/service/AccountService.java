package com.banking.api.service;

import com.banking.api.model.Account;
import com.banking.api.model.User;
import com.banking.api.repository.AccountRepository;
import com.banking.api.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

	private AccountRepository accountRepo;
	private UserRepository userRepo;
	
	public AccountService() {
		this.accountRepo = new AccountRepository();
		this.userRepo = new UserRepository();
	}
	
	public String createNewAccount(String username) {
		User user = userRepo.findByUsername(username);
		if (user == null) return null;
		//using the current time so i can get randomized account numbers 
		String accountNumber = "ACCT-" + System.currentTimeMillis();
		
		boolean success = accountRepo.createAccount(user.getid(), accountNumber);
		return success ? accountNumber : null;
	}
	//calls the method for admin
	public List<String> getAllAccountsForAdmin() {
	    return accountRepo.findAllAccountsWithUsernames();
	}
	
	// account specific details for user
	public List<String> getUserAccounts(String username) {
	    return accountRepo.findAllByUsername(username);
	}
	
	public String processTransaction(String username, String accountNumber, String type, double amount) {
		if(amount <= 0) {
			return "Amount must be larger than 0";
		}
		
		if(!type.equals("CREDIT") && !type.equals("DEBIT")) {
			return "Invalid transaction type";
		}
		//gets the account from db 
		Account account = accountRepo.findAccountByUsername(accountNumber, username);
		if(account == null) {
			return "account not found";
		}
		
		if(account.getBalance() < amount && type.equals("DEBIT")) {
			return "Insufficient balance";
		}
		
		boolean success = accountRepo.performTransaction(account.getId(), type, amount);
		
		if(success) {
			return "success";
		}else {
			return "transaction failed";
		}
	}
}