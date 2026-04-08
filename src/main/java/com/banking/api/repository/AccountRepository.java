package com.banking.api.repository;

import com.banking.api.model.Account;
import com.banking.api.util.PropertiesUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {

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
    
    public Account findAccountByUsername(String accountNumber,String username) {
    	String sql = "select a.* from accounts a JOIN users u ON a.user_id = u.id WHERE a.account_number = ? AND u.username= ? ";
    	try (Connection conn = getConnection();
        		PreparedStatement stmt = conn.prepareStatement(sql)){
        		stmt.setString(1, accountNumber);
        		stmt.setString(2, username);
        		ResultSet rs = stmt.executeQuery();
        		if(rs.next()) {
        			Account acc = new Account();
        			acc.setId(rs.getInt("id"));
        			acc.setUserId(rs.getInt("user_id"));
        			acc.setAccountNumber(rs.getString("account_number"));
        			acc.setBalance(rs.getDouble("balance"));
        			return acc;
        		}
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public boolean createAccount(int userId, String accountNumber){
    	String sql = "Insert into accounts (user_id, account_number , balance) values (?,?,0.00)";
    	try(Connection conn = getConnection();
    			PreparedStatement stmt = conn.prepareStatement(sql)){
    		stmt.setInt(1, userId);
    		stmt.setString(2, accountNumber);
    		return stmt.executeUpdate() > 0;
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public boolean performTransaction(int accountId, String type, double amount) {
    	Connection conn = null;
    	try {
    		conn = getConnection();
    		conn.setAutoCommit(false);
    		String updateSQL;
    		if(type.equals("CREDIT")) {
    			updateSQL = "UPDATE accounts Set balance = balance + ? where id = ? ";
    		}
    		else {
    			updateSQL = "UPDATE accounts Set balance = balance - ? where id = ? ";
    		}
    		try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                updateStmt.setDouble(1, amount);
                updateStmt.setInt(2, accountId);
                updateStmt.executeUpdate();
            }
    		String insertSQL = "insert into transactions(account_id , transaction_type , amount) VALUES (?,?,?)";
    		try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
                insertStmt.setInt(1, accountId);
                insertStmt.setString(2, type);
                insertStmt.setDouble(3, amount);
                insertStmt.executeUpdate();
            }
    		
    		conn.commit();
    		return true;
    	} catch(SQLException e) {
    		e.printStackTrace();
    		if (conn != null) {
                try { conn.rollback(); } catch (SQLException er) { er.printStackTrace(); }
            }
    		return false;
    	} finally {
    		if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
    	}
    }
}
