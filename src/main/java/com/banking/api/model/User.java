package com.banking.api.model;


public class User {
	private int id;
	private String username;
	private String passwordhash;
	private String role;
	
	public User() {}
	
	public User(int id, String username, String passwordhash, String role) {
		this.id = id;
		this.username = username;
		this.passwordhash =passwordhash ;
		this.role=role;
	}
	//getters setter
	public int getid()  {return id;}
	public void setid(int id) {this.id =id;}

	public String getusername()  {return username;}
	public void setusername(String username) {this.username =username;}

	public String getpasswordhash()  {return passwordhash;}
	public void setpasswordhash(String passwordhash) {this.passwordhash = passwordhash;}

	public String getrole()  {return role;}
	public void setrole(String role){this.role=role;}
	
}
