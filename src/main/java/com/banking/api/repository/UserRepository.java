package com.banking.api.repository;

import com.banking.api.model.User;
import com.banking.api.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
	
	private static final String url = PropertiesUtil.getProperty("db.url");
    private static final String db_user = PropertiesUtil.getProperty("db.username"); 
    private static final String db_pswd = PropertiesUtil.getProperty("db.password");
    
    private Connection getConnection() throws SQLException{
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    	return DriverManager.getConnection(url,db_user,db_pswd);
    }
    
    public boolean save(User user) {
    	String sql = "insert into users (username,password_hash, role) VALUES (?,?,?)";
    	
    	try(Connection conn = getConnection();
    		PreparedStatement stmt =  conn.prepareStatement(sql)){
    		
    		stmt.setString(1, user.getusername());
    		stmt.setString(2, user.getpasswordhash());
    		stmt.setString(3, user.getrole());
    		
    		int rowsaffected = stmt.executeUpdate();
    		return rowsaffected > 0;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public User findByUsername(String username) {
    	String sql = "select * from users where username = ?";
    	
    	try (Connection conn = getConnection();
    		PreparedStatement stmt = conn.prepareStatement(sql)){
    		stmt.setString(1, username);
    		
    		ResultSet rs = stmt.executeQuery();
    		
    		if(rs.next()) {
    			User user = new User();
    			user.setid(rs.getInt("id"));
    			user.setusername(rs.getString("username"));
    			user.setpasswordhash(rs.getString("password_hash"));
    			user.setrole(rs.getString("role"));
    			return user;
    		}
    		
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
}
