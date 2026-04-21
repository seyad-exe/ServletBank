package com.banking.api.repository;

import com.banking.api.model.Transaction;
import com.banking.api.util.PropertiesUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
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
    
    public List<Transaction> findTransactionsByUsername(String username, int limit, int offset) {
        List<Transaction> transactions = new ArrayList<>();
        
        String sql = "select a.account_number, t.transaction_type, t.amount, t.transaction_date " +
                     "From transactions t " +
                     "Join accounts a ON t.account_id = a.id " +
                     "Join users u ON a.user_id = u.id " +
                     "where u.username = ? " +
                     "Order by t.transaction_date desc " +
                     "LIMIT ? OFFSET ?";
        
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.accountNumber = rs.getString("account_number");
                t.type = rs.getString("transaction_type");
                t.amount = rs.getDouble("amount");
                t.date = rs.getTimestamp("transaction_date");
                transactions.add(t);
            }
        } catch (SQLException e) { 
        	e.printStackTrace();
        	}
        return transactions;
    }

}
