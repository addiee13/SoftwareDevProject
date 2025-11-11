package com.companyz.ems.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for ValidationUtil class.
 * Tests all validation methods with valid and invalid inputs.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
class ValidationUtilTest {

    @Test
    void testIsValidSSN_WithValidSSN_ReturnsTrue() {
        assertTrue(ValidationUtil.isValidSSN("123456789"));
        assertTrue(ValidationUtil.isValidSSN("000000000"));
        assertTrue(ValidationUtil.isValidSSN("999999999"));
    }

    @Test
    void testIsValidSSN_WithInvalidSSN_ReturnsFalse() {
        // Less than 9 digits
        assertFalse(ValidationUtil.isValidSSN("12345678"));
        
        // More than 9 digits
        assertFalse(ValidationUtil.isValidSSN("1234567890"));
        
        // Contains letters
        assertFalse(ValidationUtil.isValidSSN("12345678A"));
        
        // Contains dashes
        assertFalse(ValidationUtil.isValidSSN("123-45-6789"));
        
        // Empty string
        assertFalse(ValidationUtil.isValidSSN(""));
        
        // Null
        assertFalse(ValidationUtil.isValidSSN(null));
        
        // Contains spaces
        assertFalse(ValidationUtil.isValidSSN("123 456 789"));
    }

    @Test
    void testIsValidSalary_WithPositiveValue_ReturnsTrue() {
        assertTrue(ValidationUtil.isValidSalary(50000.0));
        assertTrue(ValidationUtil.isValidSalary(0.01));
        assertTrue(ValidationUtil.isValidSalary(1000000.0));
    }

    @Test
    void testIsValidSalary_WithZeroOrNegative_ReturnsFalse() {
        assertFalse(ValidationUtil.isValidSalary(0.0));
        assertFalse(ValidationUtil.isValidSalary(-1.0));
        assertFalse(ValidationUtil.isValidSalary(-50000.0));
    }

    @Test
    void testIsValidPercentage_WithPositiveValue_ReturnsTrue() {
        assertTrue(ValidationUtil.isValidPercentage(3.2));
        assertTrue(ValidationUtil.isValidPercentage(0.1));
        assertTrue(ValidationUtil.isValidPercentage(100.0));
    }

    @Test
    void testIsValidPercentage_WithZeroOrNegative_ReturnsFalse() {
        assertFalse(ValidationUtil.isValidPercentage(0.0));
        assertFalse(ValidationUtil.isValidPercentage(-1.0));
        assertFalse(ValidationUtil.isValidPercentage(-5.5));
    }

    @Test
    void testIsNotEmpty_WithNonEmptyString_ReturnsTrue() {
        assertTrue(ValidationUtil.isNotEmpty("John"));
        assertTrue(ValidationUtil.isNotEmpty("A"));
        assertTrue(ValidationUtil.isNotEmpty("  text  "));
    }

    @Test
    void testIsNotEmpty_WithEmptyOrNull_ReturnsFalse() {
        assertFalse(ValidationUtil.isNotEmpty(""));
        assertFalse(ValidationUtil.isNotEmpty("   "));
        assertFalse(ValidationUtil.isNotEmpty(null));
    }

    @Test
    void testStripSSNFormatting_RemovesDashesAndSpaces() {
        assertEquals("123456789", ValidationUtil.stripSSNFormatting("123-45-6789"));
        assertEquals("123456789", ValidationUtil.stripSSNFormatting("123 45 6789"));
        assertEquals("123456789", ValidationUtil.stripSSNFormatting("123-456-789"));
        assertEquals("123456789", ValidationUtil.stripSSNFormatting("123456789"));
        assertEquals("", ValidationUtil.stripSSNFormatting("---"));
    }

    @Test
    void testStripSSNFormatting_WithNull_ReturnsNull() {
        assertNull(ValidationUtil.stripSSNFormatting(null));
    }

    @Test
    void testIsValidSalaryRange_WithValidRange_ReturnsTrue() {
        assertTrue(ValidationUtil.isValidSalaryRange(50000, 100000));
        assertTrue(ValidationUtil.isValidSalaryRange(0, 1));
        assertTrue(ValidationUtil.isValidSalaryRange(58000, 105000));
    }

    @Test
    void testIsValidSalaryRange_WithInvalidRange_ReturnsFalse() {
        assertFalse(ValidationUtil.isValidSalaryRange(100000, 50000));
        assertFalse(ValidationUtil.isValidSalaryRange(50000, 50000));
        assertFalse(ValidationUtil.isValidSalaryRange(100, 50));
    }

    @Test
    void testIsValidEmployeeId_WithPositiveId_ReturnsTrue() {
        assertTrue(ValidationUtil.isValidEmployeeId(1));
        assertTrue(ValidationUtil.isValidEmployeeId(100));
        assertTrue(ValidationUtil.isValidEmployeeId(999999));
    }

    @Test
    void testIsValidEmployeeId_WithZeroOrNegative_ReturnsFalse() {
        assertFalse(ValidationUtil.isValidEmployeeId(0));
        assertFalse(ValidationUtil.isValidEmployeeId(-1));
        assertFalse(ValidationUtil.isValidEmployeeId(-100));
    }

    @Test
    void testIsValidMonth_WithValidMonth_ReturnsTrue() {
        assertTrue(ValidationUtil.isValidMonth(1));
        assertTrue(ValidationUtil.isValidMonth(6));
        assertTrue(ValidationUtil.isValidMonth(12));
    }

    @Test
    void testIsValidMonth_WithInvalidMonth_ReturnsFalse() {
        assertFalse(ValidationUtil.isValidMonth(0));
        assertFalse(ValidationUtil.isValidMonth(13));
        assertFalse(ValidationUtil.isValidMonth(-1));
        assertFalse(ValidationUtil.isValidMonth(100));
    }

    @Test
    void testIsValidYear_WithValidYear_ReturnsTrue() {
        assertTrue(ValidationUtil.isValidYear(2000));
        assertTrue(ValidationUtil.isValidYear(2024));
        assertTrue(ValidationUtil.isValidYear(2100));
    }

    @Test
    void testIsValidYear_WithInvalidYear_ReturnsFalse() {
        assertFalse(ValidationUtil.isValidYear(1999));
        assertFalse(ValidationUtil.isValidYear(2101));
        assertFalse(ValidationUtil.isValidYear(1900));
        assertFalse(ValidationUtil.isValidYear(3000));
    }
}
