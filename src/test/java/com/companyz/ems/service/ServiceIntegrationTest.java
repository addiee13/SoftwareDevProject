package com.companyz.ems.service;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.companyz.ems.TestDatabaseSetup;
import com.companyz.ems.exception.DatabaseException;
import com.companyz.ems.exception.ValidationException;
import com.companyz.ems.model.Employee;
import com.companyz.ems.repository.EmployeeRepository;
import com.companyz.ems.repository.PayStatementRepository;

/**
 * Integration tests for Service layer.
 * Tests service methods with actual database operations.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceIntegrationTest {

    private static EmployeeService employeeService;
    private static ReportService reportService;
    private static SalaryService salaryService;

    @BeforeAll
    static void setUpServices() {
        System.out.println("Setting up services for integration tests...");
        TestDatabaseSetup.setupTestDatabase();
        
        EmployeeRepository employeeRepository = new EmployeeRepository();
        PayStatementRepository payStatementRepository = new PayStatementRepository();
        
        employeeService = new EmployeeService(employeeRepository);
        reportService = new ReportService(employeeRepository, payStatementRepository);
        salaryService = new SalaryService(employeeRepository);
    }

    @AfterAll
    static void tearDown() {
        TestDatabaseSetup.tearDownTestDatabase();
    }

    // EmployeeService Tests

    @Test
    @Order(1)
    void testSearchEmployee_ByName_ReturnsResults() throws ValidationException, DatabaseException {
        List<Employee> results = employeeService.searchEmployee("Employee", "name");
        
        assertFalse(results.isEmpty());
        assertTrue(results.size() >= 5);
    }

    @Test
    @Order(2)
    void testSearchEmployee_BySSN_ReturnsEmployee() throws ValidationException, DatabaseException {
        List<Employee> results = employeeService.searchEmployee("111111111", "ssn");
        
        assertEquals(1, results.size());
        assertEquals("Test", results.get(0).getFirstName());
    }

    @Test
    @Order(3)
    void testSearchEmployee_ByEmpId_ReturnsEmployee() throws ValidationException, DatabaseException {
        List<Employee> results = employeeService.searchEmployee("1", "empid");
        
        assertEquals(1, results.size());
        assertEquals(1, results.get(0).getEmpId());
    }

    @Test
    @Order(4)
    void testSearchEmployee_WithInvalidSSN_ThrowsValidationException() {
        assertThrows(ValidationException.class, () -> {
            employeeService.searchEmployee("12345", "ssn");
        });
    }

    @Test
    @Order(5)
    void testAddEmployee_WithValidData_AddsSuccessfully() throws ValidationException, DatabaseException {
        Employee newEmployee = new Employee(
            "Integration", "Test", "888888888",
            "Test Role", "Test Division", 60000.0, "FULL_TIME"
        );
        
        employeeService.addEmployee(newEmployee);
        
        assertTrue(newEmployee.getEmpId() > 0);
        
        // Verify it was added
        Employee retrieved = employeeService.getEmployeeById(newEmployee.getEmpId());
        assertNotNull(retrieved);
        assertEquals("Integration", retrieved.getFirstName());
    }

    @Test
    @Order(6)
    void testAddEmployee_WithInvalidSSN_ThrowsValidationException() {
        Employee invalidEmployee = new Employee(
            "Bad", "SSN", "123",  // Invalid SSN
            "Role", "Division", 50000.0, "FULL_TIME"
        );
        
        assertThrows(ValidationException.class, () -> {
            employeeService.addEmployee(invalidEmployee);
        });
    }

    @Test
    @Order(7)
    void testUpdateEmployee_WithValidData_UpdatesSuccessfully() throws ValidationException, DatabaseException {
        Employee employee = employeeService.getEmployeeById(1);
        assertNotNull(employee);
        
        String originalJobTitle = employee.getJobTitle();
        employee.setJobTitle("Updated Title");
        
        employeeService.updateEmployee(employee);
        
        // Verify update
        Employee updated = employeeService.getEmployeeById(1);
        assertEquals("Updated Title", updated.getJobTitle());
        
        // Restore original
        employee.setJobTitle(originalJobTitle);
        employeeService.updateEmployee(employee);
    }

    // SalaryService Tests

    @Test
    @Order(8)
    void testApplySalaryIncrease_WithValidRange_UpdatesEmployees() throws ValidationException, DatabaseException {
        // Get initial salary
        Employee emp = employeeService.getEmployeeById(1);
        double initialSalary = emp.getSalary();
        
        // Apply 5% increase to salaries >= 70000 and < 80000
        int affectedCount = salaryService.applySalaryIncrease(5.0, 70000, 80000);
        
        assertTrue(affectedCount > 0);
        
        // Verify salary was updated
        Employee updated = employeeService.getEmployeeById(1);
        assertTrue(updated.getSalary() > initialSalary);
        
        // Restore original salary
        emp.setSalary(initialSalary);
        employeeService.updateEmployee(emp);
    }

    @Test
    @Order(9)
    void testApplySalaryIncrease_WithInvalidPercentage_ThrowsValidationException() {
        assertThrows(ValidationException.class, () -> {
            salaryService.applySalaryIncrease(-5.0, 50000, 100000);
        });
    }

    @Test
    @Order(10)
    void testApplySalaryIncrease_WithInvalidRange_ThrowsValidationException() {
        assertThrows(ValidationException.class, () -> {
            salaryService.applySalaryIncrease(5.0, 100000, 50000); // min > max
        });
    }

    // ReportService Tests

    @Test
    @Order(11)
    void testGenerateEmployeeReport_ReturnsEmployeesWithPayHistory() throws DatabaseException {
        List<Employee> report = reportService.generateEmployeeReport();
        
        assertFalse(report.isEmpty());
        
        // Verify pay statements are loaded
        for (Employee emp : report) {
            assertNotNull(emp.getPayStatements());
        }
    }

    @Test
    @Order(12)
    void testGeneratePayByJobTitleReport_ReturnsAggregatedData() throws ValidationException, DatabaseException {
        Map<String, Double> report = reportService.generatePayByJobTitleReport(1, 2024);
        
        assertFalse(report.isEmpty());
        assertTrue(report.containsKey("Software Engineer"));
        assertTrue(report.get("Software Engineer") > 0);
    }

    @Test
    @Order(13)
    void testGeneratePayByDivisionReport_ReturnsAggregatedData() throws ValidationException, DatabaseException {
        Map<String, Double> report = reportService.generatePayByDivisionReport(1, 2024);
        
        assertFalse(report.isEmpty());
        assertTrue(report.containsKey("Engineering"));
        assertTrue(report.get("Engineering") > 0);
    }

    @Test
    @Order(14)
    void testGeneratePayByJobTitleReport_WithInvalidMonth_ThrowsValidationException() {
        assertThrows(ValidationException.class, () -> {
            reportService.generatePayByJobTitleReport(13, 2024);
        });
    }

    @Test
    @Order(15)
    void testGeneratePayByDivisionReport_WithInvalidYear_ThrowsValidationException() {
        assertThrows(ValidationException.class, () -> {
            reportService.generatePayByDivisionReport(1, 1999);
        });
    }
}
