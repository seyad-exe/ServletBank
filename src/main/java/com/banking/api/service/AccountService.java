package com.banking.api.service;

import com.banking.api.model.Account;
import com.banking.api.repository.AccountRepository;

public class AccountService {

	private AccountRepository accountRepo;
	
	public AccountService() {
		this.accountRepo = new AccountRepository();
	}
	
	public String processTransaction(String username, String type, double amount) {
		if(amount <= 0) {
			return "Amount must be larger than 0";
		}
		
		if(!type.equals("CREDIT") && !type.equals("DEBIT")) {
			return "Invalid transaction type";
		}
		//gets the account from db 
		Account account = accountRepo.findAccountByUsername(username);
		if(account == null) {
			return "account not found";
		}
		
		if(account.getBalance() < amount && type.equals("DEBIT")) {
			return "Insufficient balance";
		}
		
		boolean success = accountRepo.performTransaction(account.getId(), type, amount);
		
		if(success) {
			return "transaction completed successfully";
		}else {
			return "transaction failed";
		}
	}
}