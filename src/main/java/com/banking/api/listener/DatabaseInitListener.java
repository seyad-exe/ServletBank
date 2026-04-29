package com.banking.api.listener;

import com.banking.api.util.DataSourceUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import java.sql.Connection;

@WebListener
public class DatabaseInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Starting Liquibase Database Migration...");
        
        try (Connection conn = DataSourceUtil.getConnection()) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(conn));
            
            // Point it to the file in your resources folder
            Liquibase liquibase = new Liquibase("db/changelog.sql", 
                    new ClassLoaderResourceAccessor(), database);
            
            liquibase.update(""); // Runs the SQL!
            System.out.println("Liquibase migration completed successfully!");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}