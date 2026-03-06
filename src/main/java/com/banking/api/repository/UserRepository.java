package com.banking.api.repository;

import com.banking.api.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
	
	private static final String url = "jdbc:mysql://localhost:3306/bank_db";
    private static final String db_user = "root"; 
    private static final String db_pswd = "";
    
    private Connection getConnection() throws SQLException{
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver")
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    	return DriverManager.getConnection(url,db_user,db_pswd);
    }
    
    public boolean save(User user) {
    	String sql = "insert into users (username,passwordhash, role) VALUES (?,?,?)";
    	
    	try(Connection conn = getConnection();
    		PreparedStatement stmt =  conn.prepareStatement(sql)){
    		
    		stmt.setString(1, sql);
    		stmt.setString(1, sql);
    		stmt.setString(1, sql);
    	}
    }
}
