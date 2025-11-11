package com.companyz.ems.service;

import com.companyz.ems.exception.DatabaseException;
import com.companyz.ems.exception.ValidationException;
import com.companyz.ems.model.SalaryRange;
import com.companyz.ems.repository.EmployeeRepository;
import com.companyz.ems.util.ValidationUtil;

/**
 * Service class for managing salary-related operations.
 * Provides methods for applying salary increases to employee groups.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class SalaryService {

    private final EmployeeRepository employeeRepository;

    /**
     * Constructor with dependency injection.
     * 
     * @param employeeRepository the employee repository
     */
    public SalaryService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Applies a percentage-based salary increase to employees within a salary range.
     * 
     * @param percentage the percentage increase to apply
     * @param minSalary the minimum salary threshold (inclusive)
     * @param maxSalary the maximum salary threshold (exclusive)
     * @return the number of employees whose salaries were updated
     * @throws ValidationException if parameters are invalid
     * @throws DatabaseException if database error occurs
     */
    public int applySalaryIncrease(double percentage, double minSalary, double maxSalary) 
            throws ValidationException, DatabaseException {
        
        // Validate percentage
        if (!ValidationUtil.isValidPercentage(percentage)) {
            throw new ValidationException("Percentage must be greater than 0");
        }

        // Validate salary range
        if (!ValidationUtil.isValidSalaryRange(minSalary, maxSalary)) {
            throw new ValidationException("Minimum salary must be less than maximum salary");
        }

        // Validate individual salary values
        if (!ValidationUtil.isValidSalary(minSalary)) {
            throw new ValidationException("Minimum salary must be greater than 0");
        }
        if (!ValidationUtil.isValidSalary(maxSalary)) {
            throw new ValidationException("Maximum salary must be greater than 0");
        }

        // Create salary range object
        SalaryRange range = new SalaryRange(minSalary, maxSalary, percentage);

        // Validate the range object
        if (!range.isValid()) {
            throw new ValidationException("Invalid salary range parameters");
        }

        try {
            // Apply the salary update with transaction support
            return employeeRepository.updateSalaryByRange(range);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error applying salary increase: " + e.getMessage(), e);
        }
    }
}
