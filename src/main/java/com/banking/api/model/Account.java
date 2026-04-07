package com.banking.api.model;

public class Account {
	private int id;
	private int userId;
	private String accountNumber;
	private double balance;
	
	public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
