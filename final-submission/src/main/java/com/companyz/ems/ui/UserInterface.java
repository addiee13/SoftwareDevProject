package com.companyz.ems.ui;

import com.companyz.ems.service.EmployeeService;
import com.companyz.ems.service.ReportService;
import com.companyz.ems.service.SalaryService;

/**
 * Abstract base class for user interface implementations.
 * Defines the contract for UI implementations (Console, JavaFX, etc.).
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public abstract class UserInterface {

    /** The employee service for managing employee operations. */
    protected EmployeeService employeeService;
    /** The report service for generating reports. */
    protected ReportService reportService;
    /** The salary service for managing salary operations. */
    protected SalaryService salaryService;

    /**
     * Constructor with service dependencies.
     * 
     * @param employeeService the employee service
     * @param reportService the report service
     * @param salaryService the salary service
     */
    public UserInterface(EmployeeService employeeService, 
                        ReportService reportService, 
                        SalaryService salaryService) {
        this.employeeService = employeeService;
        this.reportService = reportService;
        this.salaryService = salaryService;
    }

    /**
     * Starts the user interface.
     * This is the main entry point for the UI.
     */
    public abstract void start();

    /**
     * Displays the main menu.
     */
    public abstract void displayMenu();

    /**
     * Handles employee search functionality.
     */
    protected abstract void handleSearchEmployee();

    /**
     * Handles adding a new employee.
     */
    protected abstract void handleAddEmployee();

    /**
     * Handles updating an existing employee.
     */
    protected abstract void handleUpdateEmployee();

    /**
     * Handles deleting an employee.
     */
    protected abstract void handleDeleteEmployee();

    /**
     * Handles salary update by range.
     */
    protected abstract void handleSalaryUpdate();

    /**
     * Handles report generation.
     */
    protected abstract void handleGenerateReports();
}
