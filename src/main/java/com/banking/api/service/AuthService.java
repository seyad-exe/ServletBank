package com.banking.api.service;
import com.banking.api.model.User;
import com.banking.api.repository.UserRepository;

import org.mindrot.jbcrypt.BCrypt;


public class AuthService {

	private UserRepository userrepository;
	
	public AuthService(){
		this.userrepository = new UserRepository();
	}
	
	public User authenticate(String username, String plainTextPassword) {
		User user = userrepository.findByUsername(username);
		String hashedPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
		
		if(user == null) {
			return null;
		}
		
		if( hashedPassword == user.getpasswordhash() ) {
			return user;
		}
		return null;
	}
}
