package com.companyz.ems.service;

import java.util.List;
import java.util.Map;

import com.companyz.ems.exception.DatabaseException;
import com.companyz.ems.exception.ValidationException;
import com.companyz.ems.model.Employee;
import com.companyz.ems.model.PayStatement;
import com.companyz.ems.repository.EmployeeRepository;
import com.companyz.ems.repository.PayStatementRepository;
import com.companyz.ems.util.ValidationUtil;

/**
 * Service class for generating reports.
 * Provides methods for employee reports and pay aggregation reports.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class ReportService {

    private final EmployeeRepository employeeRepository;
    private final PayStatementRepository payStatementRepository;

    /**
     * Constructor with dependency injection.
     * 
     * @param employeeRepository the employee repository
     * @param payStatementRepository the pay statement repository
     */
    public ReportService(EmployeeRepository employeeRepository, 
                        PayStatementRepository payStatementRepository) {
        this.employeeRepository = employeeRepository;
        this.payStatementRepository = payStatementRepository;
    }

    /**
     * Generates a report of all full-time employees with their pay history.
     * 
     * @return list of full-time employees with pay statements loaded
     * @throws DatabaseException if database error occurs
     */
    public List<Employee> generateEmployeeReport() throws DatabaseException {
        try {
            // Get all full-time employees
            List<Employee> employees = employeeRepository.findAllFullTime();
            
            // Load pay statements for each employee
            for (Employee employee : employees) {
                List<PayStatement> payStatements = 
                    payStatementRepository.findByEmployeeId(employee.getEmpId());
                employee.setPayStatements(payStatements);
            }
            
            return employees;
        } catch (DatabaseException e) {
            throw new DatabaseException("Error generating employee report: " + e.getMessage(), e);
        }
    }

    /**
     * Generates a report of total pay by job title for a specific month and year.
     * 
     * @param month the month (1-12)
     * @param year the year
     * @return map of job title to total pay amount
     * @throws ValidationException if month or year is invalid
     * @throws DatabaseException if database error occurs
     */
    public Map<String, Double> generatePayByJobTitleReport(int month, int year) 
            throws ValidationException, DatabaseException {
        
        // Validate month and year
        if (!ValidationUtil.isValidMonth(month)) {
            throw new ValidationException("Invalid month. Month must be between 1 and 12.");
        }
        if (!ValidationUtil.isValidYear(year)) {
            throw new ValidationException("Invalid year. Year must be between 2000 and 2100.");
        }

        try {
            return payStatementRepository.getTotalPayByJobTitle(month, year);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error generating pay by job title report: " + e.getMessage(), e);
        }
    }

    /**
     * Generates a report of total pay by division for a specific month and year.
     * 
     * @param month the month (1-12)
     * @param year the year
     * @return map of division to total pay amount
     * @throws ValidationException if month or year is invalid
     * @throws DatabaseException if database error occurs
     */
    public Map<String, Double> generatePayByDivisionReport(int month, int year) 
            throws ValidationException, DatabaseException {
        
        // Validate month and year
        if (!ValidationUtil.isValidMonth(month)) {
            throw new ValidationException("Invalid month. Month must be between 1 and 12.");
        }
        if (!ValidationUtil.isValidYear(year)) {
            throw new ValidationException("Invalid year. Year must be between 2000 and 2100.");
        }

        try {
            return payStatementRepository.getTotalPayByDivision(month, year);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error generating pay by division report: " + e.getMessage(), e);
        }
    }
}
