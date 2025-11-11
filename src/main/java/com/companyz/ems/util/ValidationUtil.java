package com.companyz.ems.util;

/**
 * Utility class for validating employee data and input parameters.
 * Provides static methods for common validation operations.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class ValidationUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private ValidationUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Validates that an SSN contains exactly 9 numeric digits.
     * 
     * @param ssn the SSN to validate
     * @return true if SSN is valid (9 digits), false otherwise
     */
    public static boolean isValidSSN(String ssn) {
        if (ssn == null || ssn.isEmpty()) {
            return false;
        }
        // Check if SSN is exactly 9 characters and all are digits
        return ssn.matches("\\d{9}");
    }

    /**
     * Validates that a salary is positive.
     * 
     * @param salary the salary to validate
     * @return true if salary is greater than 0, false otherwise
     */
    public static boolean isValidSalary(double salary) {
        return salary > 0;
    }

    /**
     * Validates that a percentage is positive.
     * 
     * @param percentage the percentage to validate
     * @return true if percentage is greater than 0, false otherwise
     */
    public static boolean isValidPercentage(double percentage) {
        return percentage > 0;
    }

    /**
     * Validates that a string is not empty or null.
     * 
     * @param value the string to validate
     * @return true if string is not null and not empty, false otherwise
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Strips formatting (dashes and spaces) from SSN input.
     * Converts formats like "123-45-6789" or "123 45 6789" to "123456789".
     * 
     * @param ssn the SSN with potential formatting
     * @return the SSN with only digits
     */
    public static String stripSSNFormatting(String ssn) {
        if (ssn == null) {
            return null;
        }
        // Remove all non-digit characters
        return ssn.replaceAll("[^0-9]", "");
    }

    /**
     * Validates that a salary range is valid (min < max).
     * 
     * @param minSalary the minimum salary
     * @param maxSalary the maximum salary
     * @return true if minSalary is less than maxSalary, false otherwise
     */
    public static boolean isValidSalaryRange(double minSalary, double maxSalary) {
        return minSalary < maxSalary;
    }

    /**
     * Validates an employee ID is positive.
     * 
     * @param empId the employee ID to validate
     * @return true if empId is greater than 0, false otherwise
     */
    public static boolean isValidEmployeeId(int empId) {
        return empId > 0;
    }

    /**
     * Validates a month value (1-12).
     * 
     * @param month the month to validate
     * @return true if month is between 1 and 12, false otherwise
     */
    public static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    /**
     * Validates a year value (reasonable range).
     * 
     * @param year the year to validate
     * @return true if year is between 2000 and 2100, false otherwise
     */
    public static boolean isValidYear(int year) {
        return year >= 2000 && year <= 2100;
    }
}
