
package com.companyz.ems.repository;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.companyz.ems.TestDatabaseSetup;
import com.companyz.ems.exception.DatabaseException;
import com.companyz.ems.model.PayStatement;

/**
 * Integration tests for PayStatementRepository.
 * Tests repository methods against actual test database.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PayStatementRepositoryIntegrationTest {

    private static PayStatementRepository repository;

    @BeforeAll
    static void setUpDatabase() {
        System.out.println("Setting up test database for PayStatement tests...");
        TestDatabaseSetup.setupTestDatabase();
        repository = new PayStatementRepository();
    }

    @AfterAll
    static void tearDownDatabase() {
        TestDatabaseSetup.tearDownTestDatabase();
    }

    @Test
    @Order(1)
    void testFindByEmployeeId_WithExistingEmployee_ReturnsPayStatements() throws DatabaseException {
        List<PayStatement> statements = repository.findByEmployeeId(1);
        
        assertFalse(statements.isEmpty());
        assertEquals(2, statements.size()); // Employee 1 has 2 pay statements in test data
        
        for (PayStatement ps : statements) {
            assertEquals(1, ps.getEmpId());
        }
    }

    @Test
    @Order(2)
    void testFindByEmployeeId_WithNoPayStatements_ReturnsEmptyList() throws DatabaseException {
        List<PayStatement> statements = repository.findByEmployeeId(999);
        
        assertTrue(statements.isEmpty());
    }

    @Test
    @Order(3)
    void testGetTotalPayByJobTitle_ReturnsCorrectAggregations() throws DatabaseException {
        Map<String, Double> payByJobTitle = repository.getTotalPayByJobTitle(1, 2024);
        
        assertFalse(payByJobTitle.isEmpty());
        
        // Verify Software Engineer total (employee 1: 6250.00)
        assertTrue(payByJobTitle.containsKey("Software Engineer"));
        assertEquals(6250.00, payByJobTitle.get("Software Engineer"), 0.01);
        
        // Verify Senior Developer total (employee 2: 7916.67)
        assertTrue(payByJobTitle.containsKey("Senior Developer"));
        assertEquals(7916.67, payByJobTitle.get("Senior Developer"), 0.01);
    }

    @Test
    @Order(4)
    void testGetTotalPayByJobTitle_WithNoData_ReturnsEmptyMap() throws DatabaseException {
        Map<String, Double> payByJobTitle = repository.getTotalPayByJobTitle(12, 2025);
        
        assertTrue(payByJobTitle.isEmpty());
    }

    @Test
    @Order(5)
    void testGetTotalPayByDivision_ReturnsCorrectAggregations() throws DatabaseException {
        Map<String, Double> payByDivision = repository.getTotalPayByDivision(1, 2024);
        
        assertFalse(payByDivision.isEmpty());
        
        // Verify Engineering total (employees 1, 2, 5)
        assertTrue(payByDivision.containsKey("Engineering"));
        double engineeringTotal = payByDivision.get("Engineering");
        assertTrue(engineeringTotal > 0);
        
        // Verify Management total (employee 3)
        assertTrue(payByDivision.containsKey("Management"));
        assertEquals(9166.67, payByDivision.get("Management"), 0.01);
        
        // Verify Finance total (employee 4)
        assertTrue(payByDivision.containsKey("Finance"));
        assertEquals(5416.67, payByDivision.get("Finance"), 0.01);
    }

    @Test
    @Order(6)
    void testGetTotalPayByDivision_WithNoData_ReturnsEmptyMap() throws DatabaseException {
        Map<String, Double> payByDivision = repository.getTotalPayByDivision(12, 2025);
        
        assertTrue(payByDivision.isEmpty());
    }

    @Test
    @Order(7)
    void testFindByMonthAndYear_ReturnsCorrectStatements() throws DatabaseException {
        List<PayStatement> statements = repository.findByMonthAndYear(1, 2024);
        
        assertFalse(statements.isEmpty());
        assertEquals(5, statements.size()); // 5 pay statements in January 2024
        
        for (PayStatement ps : statements) {
            assertEquals(1, ps.getPayDate().getMonthValue());
            assertEquals(2024, ps.getPayDate().getYear());
        }
    }

    @Test
    @Order(8)
    void testFindByMonthAndYear_WithNoData_ReturnsEmptyList() throws DatabaseException {
        List<PayStatement> statements = repository.findByMonthAndYear(12, 2025);
        
        assertTrue(statements.isEmpty());
    }

    @Test
    @Order(9)
    void testFindAll_ReturnsAllPayStatements() throws DatabaseException {
        List<PayStatement> allStatements = repository.findAll();
        
        assertFalse(allStatements.isEmpty());
        assertTrue(allStatements.size() >= 7); // At least 7 pay statements in test data
    }

    @Test
    @Order(10)
    void testSave_WithNewPayStatement_InsertsSuccessfully() throws DatabaseException {
        PayStatement newStatement = new PayStatement(
            1, // empId
            5000.00,
            java.time.LocalDate.of(2024, 3, 31),
            "March 2024"
        );
        
        repository.save(newStatement);
        
        assertTrue(newStatement.getStatementId() > 0);
        
        // Verify it was saved
        PayStatement retrieved = repository.findById(newStatement.getStatementId());
        assertNotNull(retrieved);
        assertEquals(5000.00, retrieved.getAmount());
        assertEquals("March 2024", retrieved.getPayPeriod());
    }
}
