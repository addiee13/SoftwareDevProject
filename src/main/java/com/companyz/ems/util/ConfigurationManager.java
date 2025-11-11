package com.companyz.ems.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manages application configuration properties.
 * Loads database configuration from properties file and supports environment variable overrides.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class ConfigurationManager {
    private static Properties properties;
    private static final String PROPERTIES_FILE = "database.properties";

    // Static initializer to load properties on class load
    static {
        properties = new Properties();
        loadProperties();
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private ConfigurationManager() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Loads properties from the database.properties file.
     * Throws RuntimeException if the file cannot be loaded.
     */
    private static void loadProperties() {
        try (InputStream input = ConfigurationManager.class
                .getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {
            
            if (input == null) {
                throw new RuntimeException("Unable to find " + PROPERTIES_FILE);
            }
            
            properties.load(input);
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration from " + PROPERTIES_FILE, e);
        }
    }

    /**
     * Gets a property value by key.
     * First checks for environment variable override, then falls back to properties file.
     * 
     * @param key the property key
     * @return the property value, or null if not found
     */
    public static String getProperty(String key) {
        // Check for environment variable override
        String envValue = getEnvironmentVariable(key);
        if (envValue != null) {
            return envValue;
        }
        
        // Fall back to properties file
        return properties.getProperty(key);
    }

    /**
     * Gets a property value with a default fallback.
     * 
     * @param key the property key
     * @param defaultValue the default value if property not found
     * @return the property value, or defaultValue if not found
     */
    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Gets the database URL from configuration.
     * 
     * @return the database URL
     */
    public static String getDatabaseUrl() {
        return getProperty("db.url");
    }

    /**
     * Gets the database username from configuration.
     * 
     * @return the database username
     */
    public static String getDatabaseUsername() {
        return getProperty("db.username");
    }

    /**
     * Gets the database password from configuration.
     * 
     * @return the database password
     */
    public static String getDatabasePassword() {
        return getProperty("db.password", "");
    }

    /**
     * Gets the database driver class name from configuration.
     * 
     * @return the database driver class name
     */
    public static String getDatabaseDriver() {
        return getProperty("db.driver");
    }

    /**
     * Gets the connection pool size from configuration.
     * 
     * @return the connection pool size, defaults to 10
     */
    public static int getConnectionPoolSize() {
        String poolSize = getProperty("db.pool.size", "10");
        try {
            return Integer.parseInt(poolSize);
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    /**
     * Gets the connection timeout from configuration.
     * 
     * @return the connection timeout in milliseconds, defaults to 30000
     */
    public static int getConnectionTimeout() {
        String timeout = getProperty("db.connection.timeout", "30000");
        try {
            return Integer.parseInt(timeout);
        } catch (NumberFormatException e) {
            return 30000;
        }
    }

    /**
     * Checks for environment variable override.
     * Converts property key format to environment variable format.
     * Example: "db.url" becomes "DB_URL"
     * 
     * @param key the property key
     * @return the environment variable value, or null if not set
     */
    private static String getEnvironmentVariable(String key) {
        // Convert property key to environment variable format
        // db.url -> DB_URL
        String envKey = key.toUpperCase().replace('.', '_');
        return System.getenv(envKey);
    }

    /**
     * Reloads properties from the configuration file.
     * Useful for testing or runtime configuration changes.
     */
    public static void reload() {
        properties.clear();
        loadProperties();
    }
}
