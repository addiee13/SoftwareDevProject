package com.companyz.ems.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for SalaryRange model class.
 * Tests validation, calculation, and range checking methods.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
class SalaryRangeTest {

    private SalaryRange salaryRange;

    @BeforeEach
    void setUp() {
        salaryRange = new SalaryRange();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(salaryRange);
        assertEquals(0.0, salaryRange.getMinSalary());
        assertEquals(0.0, salaryRange.getMaxSalary());
        assertEquals(0.0, salaryRange.getPercentageIncrease());
    }

    @Test
    void testConstructorWithParameters() {
        SalaryRange range = new SalaryRange(58000, 105000, 3.2);
        
        assertEquals(58000, range.getMinSalary());
        assertEquals(105000, range.getMaxSalary());
        assertEquals(3.2, range.getPercentageIncrease());
    }

    @Test
    void testGettersAndSetters() {
        salaryRange.setMinSalary(50000);
        salaryRange.setMaxSalary(100000);
        salaryRange.setPercentageIncrease(5.0);

        assertEquals(50000, salaryRange.getMinSalary());
        assertEquals(100000, salaryRange.getMaxSalary());
        assertEquals(5.0, salaryRange.getPercentageIncrease());
    }

    @Test
    void testIsValid_WithValidRange_ReturnsTrue() {
        salaryRange.setMinSalary(50000);
        salaryRange.setMaxSalary(100000);
        salaryRange.setPercentageIncrease(3.2);
        
        assertTrue(salaryRange.isValid());
    }

    @Test
    void testIsValid_WithMinGreaterThanMax_ReturnsFalse() {
        salaryRange.setMinSalary(100000);
        salaryRange.setMaxSalary(50000);
        salaryRange.setPercentageIncrease(3.2);
        
        assertFalse(salaryRange.isValid());
    }

    @Test
    void testIsValid_WithMinEqualToMax_ReturnsFalse() {
        salaryRange.setMinSalary(75000);
        salaryRange.setMaxSalary(75000);
        salaryRange.setPercentageIncrease(3.2);
        
        assertFalse(salaryRange.isValid());
    }

    @Test
    void testIsValid_WithZeroPercentage_ReturnsFalse() {
        salaryRange.setMinSalary(50000);
        salaryRange.setMaxSalary(100000);
        salaryRange.setPercentageIncrease(0.0);
        
        assertFalse(salaryRange.isValid());
    }

    @Test
    void testIsValid_WithNegativePercentage_ReturnsFalse() {
        salaryRange.setMinSalary(50000);
        salaryRange.setMaxSalary(100000);
        salaryRange.setPercentageIncrease(-5.0);
        
        assertFalse(salaryRange.isValid());
    }

    @Test
    void testCalculateNewSalary() {
        salaryRange.setPercentageIncrease(10.0);
        
        double currentSalary = 50000;
        double newSalary = salaryRange.calculateNewSalary(currentSalary);
        
        assertEquals(55000.0, newSalary, 0.01);
    }

    @Test
    void testCalculateNewSalary_WithDecimalPercentage() {
        salaryRange.setPercentageIncrease(3.2);
        
        double currentSalary = 75000;
        double newSalary = salaryRange.calculateNewSalary(currentSalary);
        
        assertEquals(77400.0, newSalary, 0.01);
    }

    @Test
    void testCalculateNewSalary_WithLargePercentage() {
        salaryRange.setPercentageIncrease(50.0);
        
        double currentSalary = 60000;
        double newSalary = salaryRange.calculateNewSalary(currentSalary);
        
        assertEquals(90000.0, newSalary, 0.01);
    }

    @Test
    void testIsInRange_WithSalaryInRange_ReturnsTrue() {
        salaryRange.setMinSalary(58000);
        salaryRange.setMaxSalary(105000);
        
        assertTrue(salaryRange.isInRange(58000));  // At minimum
        assertTrue(salaryRange.isInRange(75000));  // In middle
        assertTrue(salaryRange.isInRange(104999)); // Just below max
    }

    @Test
    void testIsInRange_WithSalaryOutOfRange_ReturnsFalse() {
        salaryRange.setMinSalary(58000);
        salaryRange.setMaxSalary(105000);
        
        assertFalse(salaryRange.isInRange(57999));  // Below minimum
        assertFalse(salaryRange.isInRange(105000)); // At maximum (exclusive)
        assertFalse(salaryRange.isInRange(110000)); // Above maximum
        assertFalse(salaryRange.isInRange(50000));  // Well below
    }

    @Test
    void testIsInRange_WithBoundaryValues() {
        salaryRange.setMinSalary(50000);
        salaryRange.setMaxSalary(100000);
        
        // Minimum is inclusive
        assertTrue(salaryRange.isInRange(50000));
        
        // Maximum is exclusive
        assertFalse(salaryRange.isInRange(100000));
    }

    @Test
    void testToString() {
        salaryRange.setMinSalary(58000);
        salaryRange.setMaxSalary(105000);
        salaryRange.setPercentageIncrease(3.2);
        
        String result = salaryRange.toString();
        
        assertTrue(result.contains("minSalary=58000"));
        assertTrue(result.contains("maxSalary=105000"));
        assertTrue(result.contains("percentageIncrease=3.2"));
    }

    @Test
    void testRealWorldScenario_ThreePointTwoPercent() {
        // Test the example from requirements: 3.2% for salary >= 58K and < 105K
        SalaryRange range = new SalaryRange(58000, 105000, 3.2);
        
        assertTrue(range.isValid());
        
        // Test employee with $75,000 salary
        double salary75k = 75000;
        assertTrue(range.isInRange(salary75k));
        double newSalary75k = range.calculateNewSalary(salary75k);
        assertEquals(77400.0, newSalary75k, 0.01);
        
        // Test employee with $95,000 salary
        double salary95k = 95000;
        assertTrue(range.isInRange(salary95k));
        double newSalary95k = range.calculateNewSalary(salary95k);
        assertEquals(98040.0, newSalary95k, 0.01);
        
        // Test employee below range
        assertFalse(range.isInRange(55000));
        
        // Test employee at or above range
        assertFalse(range.isInRange(105000));
        assertFalse(range.isInRange(120000));
    }
}
