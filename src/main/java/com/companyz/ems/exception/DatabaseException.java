package com.companyz.ems.exception;

/**
 * Exception thrown when database operations fail.
 * This includes connection errors, SQL execution errors, and data access issues.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class DatabaseException extends EMSException {

    /**
     * Constructs a new DatabaseException with the specified detail message.
     * 
     * @param message the detail message
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Constructs a new DatabaseException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception (typically SQLException)
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new DatabaseException with the specified cause.
     * 
     * @param cause the cause of the exception
     */
    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
