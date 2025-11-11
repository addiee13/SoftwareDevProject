package com.companyz.ems.service;

import java.util.List;

import com.companyz.ems.exception.DatabaseException;
import com.companyz.ems.exception.ValidationException;
import com.companyz.ems.model.Employee;
import com.companyz.ems.repository.EmployeeRepository;
import com.companyz.ems.util.ValidationUtil;

/**
 * Service class for managing employee-related business operations.
 * Provides methods for searching, adding, updating, and deleting employees
 * with validation and error handling.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * Constructor with dependency injection.
     * 
     * @param employeeRepository the employee repository
     */
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Searches for employees based on the provided search criteria.
     * 
     * @param searchTerm the value to search for (name, SSN, or employee ID)
     * @param searchType the type of search ("name", "ssn", or "empid")
     * @return list of matching employees, empty list if no matches found
     * @throws ValidationException if searchTerm is empty or searchType is invalid
     * @throws DatabaseException if database error occurs during search
     */
    public List<Employee> searchEmployee(String searchTerm, String searchType) 
            throws ValidationException, DatabaseException {
        
        // Validate search term
        if (!ValidationUtil.isNotEmpty(searchTerm)) {
            throw new ValidationException("Search term cannot be empty");
        }

        // Validate search type
        if (!ValidationUtil.isNotEmpty(searchType)) {
            throw new ValidationException("Search type cannot be empty");
        }

        searchType = searchType.toLowerCase().trim();

        try {
            switch (searchType) {
                case "name":
                    return employeeRepository.findByName(searchTerm);
                
                case "ssn":
                    // Strip formatting from SSN if present
                    String cleanedSSN = ValidationUtil.stripSSNFormatting(searchTerm);
                    if (!ValidationUtil.isValidSSN(cleanedSSN)) {
                        throw new ValidationException("Invalid SSN format. SSN must be 9 digits.");
                    }
                    Employee emp = employeeRepository.findBySsn(cleanedSSN);
                    return emp != null ? List.of(emp) : List.of();
                
                case "empid":
                    try {
                        int empId = Integer.parseInt(searchTerm);
                        if (!ValidationUtil.isValidEmployeeId(empId)) {
                            throw new ValidationException("Employee ID must be a positive number");
                        }
                        Employee employee = employeeRepository.findByEmpId(empId);
                        return employee != null ? List.of(employee) : List.of();
                    } catch (NumberFormatException e) {
                        throw new ValidationException("Employee ID must be a valid number");
                    }
                
                default:
                    throw new ValidationException("Invalid search type. Use 'name', 'ssn', or 'empid'");
            }
        } catch (DatabaseException e) {
            throw new DatabaseException("Error searching for employee: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a new employee to the system.
     * Validates all required fields before saving.
     * 
     * @param employee the employee to add
     * @throws ValidationException if validation fails
     * @throws DatabaseException if database error occurs
     */
    public void addEmployee(Employee employee) throws ValidationException, DatabaseException {
        // Validate required fields
        validateEmployee(employee);

        try {
            employeeRepository.save(employee);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error adding employee: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing employee's information.
     * Validates all required fields before updating.
     * 
     * @param employee the employee with updated information
     * @throws ValidationException if validation fails
     * @throws DatabaseException if database error occurs
     */
    public void updateEmployee(Employee employee) throws ValidationException, DatabaseException {
        // Validate employee ID
        if (!ValidationUtil.isValidEmployeeId(employee.getEmpId())) {
            throw new ValidationException("Invalid employee ID");
        }

        // Validate required fields
        validateEmployee(employee);

        try {
            employeeRepository.update(employee);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error updating employee: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes an employee from the system.
     * 
     * @param empId the employee ID to delete
     * @throws ValidationException if employee ID is invalid
     * @throws DatabaseException if database error occurs
     */
    public void deleteEmployee(int empId) throws ValidationException, DatabaseException {
        if (!ValidationUtil.isValidEmployeeId(empId)) {
            throw new ValidationException("Invalid employee ID");
        }

        try {
            employeeRepository.delete(empId);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error deleting employee: " + e.getMessage(), e);
        }
    }

    /**
     * Gets an employee by ID.
     * 
     * @param empId the employee ID
     * @return the employee, or null if not found
     * @throws ValidationException if employee ID is invalid
     * @throws DatabaseException if database error occurs
     */
    public Employee getEmployeeById(int empId) throws ValidationException, DatabaseException {
        if (!ValidationUtil.isValidEmployeeId(empId)) {
            throw new ValidationException("Invalid employee ID");
        }

        try {
            return employeeRepository.findById(empId);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error retrieving employee: " + e.getMessage(), e);
        }
    }

    /**
     * Gets all employees.
     * 
     * @return list of all employees
     * @throws DatabaseException if database error occurs
     */
    public List<Employee> getAllEmployees() throws DatabaseException {
        try {
            return employeeRepository.findAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error retrieving all employees: " + e.getMessage(), e);
        }
    }

    /**
     * Validates an employee object.
     * Checks all required fields and data formats.
     * 
     * @param employee the employee to validate
     * @throws ValidationException if validation fails
     */
    private void validateEmployee(Employee employee) throws ValidationException {
        if (employee == null) {
            throw new ValidationException("Employee cannot be null");
        }

        // Validate first name
        if (!ValidationUtil.isNotEmpty(employee.getFirstName())) {
            throw new ValidationException("First name is required");
        }

        // Validate last name
        if (!ValidationUtil.isNotEmpty(employee.getLastName())) {
            throw new ValidationException("Last name is required");
        }

        // Validate and clean SSN
        String ssn = employee.getSsn();
        if (!ValidationUtil.isNotEmpty(ssn)) {
            throw new ValidationException("SSN is required");
        }
        
        // Strip formatting and validate
        String cleanedSSN = ValidationUtil.stripSSNFormatting(ssn);
        if (!ValidationUtil.isValidSSN(cleanedSSN)) {
            throw new ValidationException("Invalid SSN format. SSN must be exactly 9 digits.");
        }
        // Update employee with cleaned SSN
        employee.setSsn(cleanedSSN);

        // Validate job title
        if (!ValidationUtil.isNotEmpty(employee.getJobTitle())) {
            throw new ValidationException("Job title is required");
        }

        // Validate division
        if (!ValidationUtil.isNotEmpty(employee.getDivision())) {
            throw new ValidationException("Division is required");
        }

        // Validate salary
        if (!ValidationUtil.isValidSalary(employee.getSalary())) {
            throw new ValidationException("Salary must be greater than 0");
        }

        // Validate employment type
        if (!ValidationUtil.isNotEmpty(employee.getEmploymentType())) {
            throw new ValidationException("Employment type is required");
        }
    }
}
