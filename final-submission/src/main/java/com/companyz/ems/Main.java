package com.companyz.ems;

import com.companyz.ems.repository.EmployeeRepository;
import com.companyz.ems.repository.PayStatementRepository;
import com.companyz.ems.service.EmployeeService;
import com.companyz.ems.service.ReportService;
import com.companyz.ems.service.SalaryService;
import com.companyz.ems.ui.ConsoleUI;
import com.companyz.ems.ui.JavaFxUI;
import com.companyz.ems.ui.UserInterface;
import com.companyz.ems.util.DatabaseConnection;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main entry point for the Employee Management System.
 * Initializes all components and starts the user interface.
 * Supports both Console and JavaFX GUI modes.
 * 
 * Usage:
 * - Console mode (default): java com.companyz.ems.Main
 * - JavaFX mode: java com.companyz.ems.Main --gui
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class Main extends Application {
    
    private static EmployeeService employeeService;
    private static ReportService reportService;
    private static SalaryService salaryService;
    
    /**
     * Main method - application entry point.
     * 
     * @param args command line arguments: --gui for JavaFX mode, otherwise Console mode
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
            employeeService = new EmployeeService(employeeRepository);
            reportService = new ReportService(employeeRepository, payStatementRepository);
            salaryService = new SalaryService(employeeRepository);
            
            // Check if GUI mode is requested
            boolean useGUI = args.length > 0 && args[0].equals("--gui");
            
            if (useGUI) {
                System.out.println("Starting JavaFX GUI mode...");
                launch(args);
            } else {
                System.out.println("Starting Console mode...");
                System.out.println("(Use --gui argument to start in GUI mode)");
                UserInterface ui = new ConsoleUI(employeeService, reportService, salaryService);
                ui.start();
            }
            
        } catch (Exception e) {
            System.err.println("\nFATAL ERROR: Application failed to start");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * JavaFX Application start method.
     * Called when running in GUI mode.
     * 
     * @param primaryStage the primary stage
     */
    @Override
    public void start(Stage primaryStage) {
        JavaFxUI javaFxUI = new JavaFxUI(employeeService, reportService, salaryService);
        javaFxUI.start(primaryStage);
    }
}
