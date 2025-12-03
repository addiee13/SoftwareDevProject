package com.companyz.ems.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Employee model class.
 * Tests object creation, getters, setters, and validation.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
class EmployeeTest {

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(employee);
        assertNotNull(employee.getPayStatements());
        assertEquals("FULL_TIME", employee.getEmploymentType());
        assertTrue(employee.getPayStatements().isEmpty());
    }

    @Test
    void testConstructorWithoutEmpId() {
        Employee emp = new Employee("John", "Doe", "123456789", 
                                   "Software Engineer", "Engineering", 
                                   75000.0, "FULL_TIME");
        
        assertEquals("John", emp.getFirstName());
        assertEquals("Doe", emp.getLastName());
        assertEquals("123456789", emp.getSsn());
        assertEquals("Software Engineer", emp.getJobTitle());
        assertEquals("Engineering", emp.getDivision());
        assertEquals(75000.0, emp.getSalary());
        assertEquals("FULL_TIME", emp.getEmploymentType());
        assertNotNull(emp.getPayStatements());
    }

    @Test
    void testConstructorWithEmpId() {
        Employee emp = new Employee(1, "John", "Doe", "123456789", 
                                   "Software Engineer", "Engineering", 
                                   75000.0, "FULL_TIME");
        
        assertEquals(1, emp.getEmpId());
        assertEquals("John", emp.getFirstName());
        assertEquals("Doe", emp.getLastName());
        assertEquals("123456789", emp.getSsn());
        assertEquals("Software Engineer", emp.getJobTitle());
        assertEquals("Engineering", emp.getDivision());
        assertEquals(75000.0, emp.getSalary());
        assertEquals("FULL_TIME", emp.getEmploymentType());
    }

    @Test
    void testGettersAndSetters() {
        employee.setEmpId(100);
        employee.setFirstName("Jane");
        employee.setLastName("Smith");
        employee.setSsn("987654321");
        employee.setJobTitle("Manager");
        employee.setDivision("Management");
        employee.setSalary(95000.0);
        employee.setEmploymentType("FULL_TIME");

        assertEquals(100, employee.getEmpId());
        assertEquals("Jane", employee.getFirstName());
        assertEquals("Smith", employee.getLastName());
        assertEquals("987654321", employee.getSsn());
        assertEquals("Manager", employee.getJobTitle());
        assertEquals("Management", employee.getDivision());
        assertEquals(95000.0, employee.getSalary());
        assertEquals("FULL_TIME", employee.getEmploymentType());
    }

    @Test
    void testGetFullName() {
        employee.setFirstName("John");
        employee.setLastName("Doe");
        
        assertEquals("John Doe", employee.getFullName());
    }

    @Test
    void testAddPayStatement() {
        PayStatement ps1 = new PayStatement(1, 1, 5000.0, 
                                           java.time.LocalDate.now(), "January 2024");
        PayStatement ps2 = new PayStatement(2, 1, 5000.0, 
                                           java.time.LocalDate.now(), "February 2024");
        
        employee.addPayStatement(ps1);
        employee.addPayStatement(ps2);
        
        assertEquals(2, employee.getPayStatements().size());
        assertTrue(employee.getPayStatements().contains(ps1));
        assertTrue(employee.getPayStatements().contains(ps2));
    }

    @Test
    void testSetPayStatements() {
        PayStatement ps1 = new PayStatement(1, 1, 5000.0, 
                                           java.time.LocalDate.now(), "January 2024");
        PayStatement ps2 = new PayStatement(2, 1, 5000.0, 
                                           java.time.LocalDate.now(), "February 2024");
        
        java.util.List<PayStatement> statements = java.util.Arrays.asList(ps1, ps2);
        employee.setPayStatements(statements);
        
        assertEquals(2, employee.getPayStatements().size());
        assertEquals(statements, employee.getPayStatements());
    }

    @Test
    void testToString() {
        employee.setEmpId(1);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setSsn("123456789");
        employee.setJobTitle("Engineer");
        employee.setDivision("Engineering");
        employee.setSalary(75000.0);
        employee.setEmploymentType("FULL_TIME");
        
        String result = employee.toString();
        
        assertTrue(result.contains("empId=1"));
        assertTrue(result.contains("firstName='John'"));
        assertTrue(result.contains("lastName='Doe'"));
        assertTrue(result.contains("ssn='123456789'"));
        assertTrue(result.contains("salary=75000.0"));
    }

    @Test
    void testSSNValidation() {
        // Valid SSN - 9 digits
        employee.setSsn("123456789");
        assertEquals("123456789", employee.getSsn());
        assertEquals(9, employee.getSsn().length());
        assertTrue(employee.getSsn().matches("\\d{9}"));
    }
}
