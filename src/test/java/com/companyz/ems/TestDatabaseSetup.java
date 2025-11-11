package com.companyz.ems;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

import com.companyz.ems.util.DatabaseConnection;

/**
 * Utility class for setting up and tearing down test database.
 * Provides methods to initialize test data before tests and clean up after.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class TestDatabaseSetup {

    /**
     * Sets up the test database with schema and test data.
     * Should be called before running integration tests.
     */
    public static void setupTestDatabase() {
        try {
            executeSQLScript("test_schema.sql");
            executeSQLScript("test_data.sql");
            System.out.println("Test database setup completed successfully");
        } catch (Exception e) {
            System.err.println("Failed to setup test database: " + e.getMessage());
            throw new RuntimeException("Test database setup failed", e);
        }
    }

    /**
     * Cleans up test data from the database.
     * Should be called after integration tests complete.
     */
    public static void tearDownTestDatabase() {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            
            stmt.execute("DELETE FROM paystatement");
            stmt.execute("DELETE FROM employee");
            
            stmt.close();
            conn.close();
            
            System.out.println("Test database cleanup completed");
        } catch (Exception e) {
            System.err.println("Failed to cleanup test database: " + e.getMessage());
        }
    }

    /**
     * Executes a SQL script file.
     * 
     * @param scriptName the name of the SQL script file in test/resources
     */
    private static void executeSQLScript(String scriptName) throws Exception {
        InputStream is = TestDatabaseSetup.class.getClassLoader()
                .getResourceAsStream(scriptName);
        
        if (is == null) {
            throw new RuntimeException("SQL script not found: " + scriptName);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sql = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            // Skip comments and empty lines
            if (line.isEmpty() || line.startsWith("--")) {
                continue;
            }
            sql.append(line).append(" ");
        }
        reader.close();

        // Execute SQL statements
        Connection conn = DatabaseConnection.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        
        String[] statements = sql.toString().split(";");
        for (String statement : statements) {
            statement = statement.trim();
            if (!statement.isEmpty()) {
                stmt.execute(statement);
            }
        }
        
        stmt.close();
        conn.close();
    }
}
