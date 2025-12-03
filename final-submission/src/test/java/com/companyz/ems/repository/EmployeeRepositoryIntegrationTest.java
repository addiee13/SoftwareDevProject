package com.companyz.ems.repository;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.companyz.ems.TestDatabaseSetup;
import com.companyz.ems.exception.DatabaseException;
import com.companyz.ems.model.Employee;
import com.companyz.ems.model.SalaryRange;

/**
 * Integration tests for EmployeeRepository.
 * Tests repository methods against actual test database.
 * 
 * Note: These tests require a running MySQL instance with test database.
 * Run test_schema.sql and test_data.sql before executing tests.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeRepositoryIntegrationTest {

    private static EmployeeRepository repository;

    @BeforeAll
    static void setUpDatabase() {
        // Note: Ensure test database is configured in test/resources/database.properties
        System.out.println("Setting up test database...");
        TestDatabaseSetup.setupTestDatabase();
        repository = new EmployeeRepository();
    }

    @AfterAll
    static void tearDownDatabase() {
        TestDatabaseSetup.tearDownTestDatabase();
    }

    @Test
    @Order(1)
    void testFindById_WithExistingEmployee_ReturnsEmployee() throws DatabaseException {
        Employee employee = repository.findById(1);
        
        assertNotNull(employee);
        assertEquals(1, employee.getEmpId());
        assertEquals("Test", employee.getFirstName());
        assertEquals("Employee1", employee.getLastName());
        assertEquals("111111111", employee.getSsn());
    }

    @Test
    @Order(2)
    void testFindById_WithNonExistentEmployee_ReturnsNull() throws DatabaseException {
        Employee employee = repository.findById(999);
        
        assertNull(employee);
    }

    @Test
    @Order(3)
    void testFindByName_WithMatchingName_ReturnsEmployees() throws DatabaseException {
        List<Employee> employees = repository.findByName("Employee");
        
        assertFalse(employees.isEmpty());
        assertTrue(employees.size() >= 5);
    }

    @Test
    @Order(4)
    void testFindBySsn_WithExistingSSN_ReturnsEmployee() throws DatabaseException {
        Employee employee = repository.findBySsn("111111111");
        
        assertNotNull(employee);
        assertEquals("Test", employee.getFirstName());
        assertEquals("Employee1", employee.getLastName());
    }

    @Test
    @Order(5)
    void testFindBySsn_WithNonExistentSSN_ReturnsNull() throws DatabaseException {
        Employee employee = repository.findBySsn("999999999");
        
        assertNull(employee);
    }

    @Test
    @Order(6)
    void testSave_WithNewEmployee_InsertsAndReturnsGeneratedId() throws DatabaseException {
        Employee newEmployee = new Employee(
            "New", "TestEmployee", "666666666",
            "Test Engineer", "Testing", 70000.0, "FULL_TIME"
        );
        
        repository.save(newEmployee);
        
        assertTrue(newEmployee.getEmpId() > 0);
        
        // Verify it was saved
        Employee retrieved = repository.findById(newEmployee.getEmpId());
        assertNotNull(retrieved);
        assertEquals("New", retrieved.getFirstName());
        assertEquals("666666666", retrieved.getSsn());
    }

    @Test
    @Order(7)
    void testUpdate_WithExistingEmployee_UpdatesSuccessfully() throws DatabaseException {
        Employee employee = repository.findById(1);
        assertNotNull(employee);
        
        String originalFirstName = employee.getFirstName();
        employee.setFirstName("Updated");
        employee.setSalary(80000.0);
        
        repository.update(employee);
        
        // Verify update
        Employee updated = repository.findById(1);
        assertEquals("Updated", updated.getFirstName());
        assertEquals(80000.0, updated.getSalary());
        
        // Restore original
        employee.setFirstName(originalFirstName);
        employee.setSalary(75000.0);
        repository.update(employee);
    }

    @Test
    @Order(8)
    void testUpdateSalaryByRange_UpdatesOnlyEmployeesInRange() throws DatabaseException {
        // Get initial salaries
        Employee emp1 = repository.findById(1); // 75000
        Employee emp5 = repository.findById(5); // 55000
        
        double initialSalary1 = emp1.getSalary();
        double initialSalary5 = emp5.getSalary();
        
        // Apply 10% increase to salaries >= 60000 and < 100000
        SalaryRange range = new SalaryRange(60000, 100000, 10.0);
        int affectedCount = repository.updateSalaryByRange(range);
        
        // Should affect employees with salaries in range
        assertTrue(affectedCount > 0);
        
        // Verify employee 1 (75000) was updated
        Employee updated1 = repository.findById(1);
        assertTrue(updated1.getSalary() > initialSalary1);
        
        // Verify employee 5 (55000) was NOT updated
        Employee updated5 = repository.findById(5);
        assertEquals(initialSalary5, updated5.getSalary());
        
        // Restore original salaries
        emp1.setSalary(initialSalary1);
        repository.update(emp1);
    }

    @Test
    @Order(9)
    void testFindAllFullTime_ReturnsOnlyFullTimeEmployees() throws DatabaseException {
        List<Employee> fullTimeEmployees = repository.findAllFullTime();
        
        assertFalse(fullTimeEmployees.isEmpty());
        
        // Verify all are full-time
        for (Employee emp : fullTimeEmployees) {
            assertEquals("FULL_TIME", emp.getEmploymentType());
        }
    }

    @Test
    @Order(10)
    void testDelete_WithExistingEmployee_DeletesSuccessfully() throws DatabaseException {
        // Create a temporary employee to delete
        Employee tempEmployee = new Employee(
            "Temp", "Delete", "777777777",
            "Temp Job", "Temp Division", 50000.0, "FULL_TIME"
        );
        repository.save(tempEmployee);
        int tempId = tempEmployee.getEmpId();
        
        // Verify it exists
        assertNotNull(repository.findById(tempId));
        
        // Delete it
        repository.delete(tempId);
        
        // Verify it's gone
        assertNull(repository.findById(tempId));
    }

    @Test
    @Order(11)
    void testDelete_WithNonExistentEmployee_ThrowsException() {
        assertThrows(DatabaseException.class, () -> {
            repository.delete(99999);
        });
    }

    @Test
    @Order(12)
    void testFindAll_ReturnsAllEmployees() throws DatabaseException {
        List<Employee> allEmployees = repository.findAll();
        
        assertFalse(allEmployees.isEmpty());
        assertTrue(allEmployees.size() >= 5);
    }
}
