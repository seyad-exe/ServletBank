package com.banking.api.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtil {
	private static HikariDataSource dataSource;
	
	static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(PropertiesUtil.getProperty("db.url"));
        config.setUsername(PropertiesUtil.getProperty("db.username"));
        config.setPassword(PropertiesUtil.getProperty("db.password"));
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        
        // pool settings
        config.setMaximumPoolSize(10); // holds 10 upto connections
        config.setMinimumIdle(2); // minimum 2 connections
        
        dataSource = new HikariDataSource(config);
    }
	public static Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // gets a connection from the pool
    }
}
