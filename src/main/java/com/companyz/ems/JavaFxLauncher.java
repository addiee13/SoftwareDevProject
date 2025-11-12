package com.companyz.ems;

import com.companyz.ems.repository.EmployeeRepository;
import com.companyz.ems.repository.PayStatementRepository;
import com.companyz.ems.service.EmployeeService;
import com.companyz.ems.service.ReportService;
import com.companyz.ems.service.SalaryService;
import com.companyz.ems.ui.JavaFxUI;
import com.companyz.ems.util.DatabaseConnection;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX Application launcher for the Employee Management System.
 * Separate launcher to properly initialize JavaFX on macOS.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class JavaFxLauncher extends Application {
    
    private static EmployeeService employeeService;
    private static ReportService reportService;
    private static SalaryService salaryService;
    
    /**
     * Main method to launch JavaFX application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Employee Management System - JavaFX Mode");
        System.out.println("Initializing...");
        
        try {
            // Test database connection
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
            employeeService = new EmployeeService(employeeRepository);
            reportService = new ReportService(employeeRepository, payStatementRepository);
            salaryService = new SalaryService(employeeRepository);
            
            System.out.println("Services initialized successfully!");
            System.out.println("Launching JavaFX GUI...");
            
            // Launch JavaFX
            launch(args);
            
        } catch (Exception e) {
            System.err.println("ERROR: Failed to initialize application");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * JavaFX start method - called by JavaFX framework.
     * 
     * @param primaryStage the primary stage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Creating JavaFX UI...");
            JavaFxUI ui = new JavaFxUI(employeeService, reportService, salaryService);
            ui.start(primaryStage);
            System.out.println("JavaFX GUI launched successfully!");
        } catch (Exception e) {
            System.err.println("ERROR: Failed to start JavaFX UI");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Called when the application is stopped.
     */
    @Override
    public void stop() {
        System.out.println("Application closing...");
    }
}
