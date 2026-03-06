package com.banking.api.service;

import com.banking.api.model.User;
import com.banking.api.repository.UserRepository;

import org.mindrot.jbcrypt.BCrypt;

public class UserService {
	//userrepository is needed for the service to work
	private UserRepository userRepository;
	
	public UserService() {
		this.userRepository = new UserRepository();
	}
	
	public boolean registerUser(String username, String plainTextPassword) {
		if (userRepository.findByUsername(username) != null) {
			return false;
		}
		String hashedPassword = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
		
		User newuser = new User(username, hashedPassword,"USER");
		
		return userRepository.save(newuser);
	}
}
