package com.companyz.ems.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class for managing database connections.
 * Provides thread-safe access to JDBC connections using configuration from ConfigurationManager.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static final Object lock = new Object();

    /**
     * Private constructor to prevent instantiation.
     * Loads the JDBC driver.
     */
    private DatabaseConnection() {
        try {
            // Load MySQL JDBC driver
            Class.forName(ConfigurationManager.getDatabaseDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    /**
     * Gets the singleton instance of DatabaseConnection.
     * Thread-safe implementation using double-checked locking.
     * 
     * @return the DatabaseConnection instance
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    /**
     * Creates and returns a new database connection.
     * Uses configuration from ConfigurationManager.
     * 
     * @return a Connection object to the database
     * @throws SQLException if connection cannot be established
     */
    public synchronized Connection getConnection() throws SQLException {
        String url = ConfigurationManager.getDatabaseUrl();
        String username = ConfigurationManager.getDatabaseUsername();
        String password = ConfigurationManager.getDatabasePassword();

        if (url == null || url.isEmpty()) {
            throw new SQLException("Database URL is not configured");
        }

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database: " + e.getMessage(), e);
        }
    }

    /**
     * Closes a database connection properly.
     * Handles null connections gracefully.
     * 
     * @param conn the connection to close
     */
    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Tests the database connection.
     * Useful for verifying configuration and connectivity.
     * 
     * @return true if connection is successful, false otherwise
     */
    public boolean testConnection() {
        Connection conn = null;
        try {
            conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * Gets connection information for debugging.
     * Does not include sensitive information like passwords.
     * 
     * @return connection information string
     */
    public String getConnectionInfo() {
        return "Database URL: " + ConfigurationManager.getDatabaseUrl() + "\n" +
               "Username: " + ConfigurationManager.getDatabaseUsername() + "\n" +
               "Driver: " + ConfigurationManager.getDatabaseDriver();
    }
}
