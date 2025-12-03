package com.companyz.ems.exception;

/**
 * Base exception class for the Employee Management System.
 * All custom exceptions in the EMS extend this class.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class EMSException extends Exception {

    /**
     * Constructs a new EMSException with the specified detail message.
     * 
     * @param message the detail message
     */
    public EMSException(String message) {
        super(message);
    }

    /**
     * Constructs a new EMSException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public EMSException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new EMSException with the specified cause.
     * 
     * @param cause the cause of the exception
     */
    public EMSException(Throwable cause) {
        super(cause);
    }
}
