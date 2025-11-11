package com.companyz.ems;

import com.companyz.ems.repository.EmployeeRepository;
import com.companyz.ems.repository.PayStatementRepository;
import com.companyz.ems.service.EmployeeService;
import com.companyz.ems.service.ReportService;
import com.companyz.ems.service.SalaryService;
import com.companyz.ems.ui.ConsoleUI;
import com.companyz.ems.ui.UserInterface;
import com.companyz.ems.util.DatabaseConnection;

/**
 * Main entry point for the Employee Management System.
 * Initializes all components and starts the user interface.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class Main {
    
    /**
     * Main method - application entry point.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Test database connection
            System.out.println("Employee Management System - Starting...");
            System.out.println("Connecting to database...");
            
            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            if (!dbConnection.testConnection()) {
                System.err.println("ERROR: Failed to connect to database.");
                System.err.println("Please check your database configuration in database.properties");
                System.err.println("\nConnection Info:");
                System.err.println(dbConnection.getConnectionInfo());
                System.exit(1);
            }
            
            System.out.println("Database connection successful!");
            
            // Initialize repositories
            EmployeeRepository employeeRepository = new EmployeeRepository();
            PayStatementRepository payStatementRepository = new PayStatementRepository();
            
            // Initialize services
            EmployeeService employeeService = new EmployeeService(employeeRepository);
            ReportService reportService = new ReportService(employeeRepository, payStatementRepository);
            SalaryService salaryService = new SalaryService(employeeRepository);
            
            // Initialize and start UI
            UserInterface ui = new ConsoleUI(employeeService, reportService, salaryService);
            ui.start();
            
        } catch (Exception e) {
            System.err.println("\nFATAL ERROR: Application failed to start");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
