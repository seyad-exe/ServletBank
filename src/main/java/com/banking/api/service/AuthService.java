package com.banking.api.service;
import com.banking.api.model.User;
import com.banking.api.repository.UserRepository;

import org.mindrot.jbcrypt.BCrypt;


public class AuthService {

	private UserRepository userrepository;
	
	public AuthService(){
		this.userrepository = new UserRepository();
	}
	
}
