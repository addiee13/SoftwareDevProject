package com.companyz.ems.ui;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.companyz.ems.exception.DatabaseException;
import com.companyz.ems.exception.ValidationException;
import com.companyz.ems.model.Employee;
import com.companyz.ems.model.PayStatement;
import com.companyz.ems.service.EmployeeService;
import com.companyz.ems.service.ReportService;
import com.companyz.ems.service.SalaryService;

/**
 * Console-based user interface implementation.
 * Provides text-based menu system for all EMS features.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class ConsoleUI extends UserInterface {

    private Scanner scanner;
    private boolean running;

    /**
     * Constructor with service dependencies.
     * 
     * @param employeeService the employee service
     * @param reportService the report service
     * @param salaryService the salary service
     */
    public ConsoleUI(EmployeeService employeeService, 
                    ReportService reportService, 
                    SalaryService salaryService) {
        super(employeeService, reportService, salaryService);
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    /**
     * Starts the console UI.
     */
    @Override
    public void start() {
        System.out.println("===========================================");
        System.out.println("  Employee Management System - Company Z");
        System.out.println("===========================================\n");

        while (running) {
            displayMenu();
        }

        scanner.close();
        System.out.println("\nThank you for using the Employee Management System!");
    }

    /**
     * Displays the main menu and handles user selection.
     */
    @Override
    public void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Search Employee");
        System.out.println("2. Add New Employee");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Update Salaries by Range");
        System.out.println("6. Generate Reports");
        System.out.println("0. Exit");
        System.out.print("\nEnter your choice: ");


        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    handleSearchEmployee();
                    break;
                case 2:
                    handleAddEmployee();
                    break;
                case 3:
                    handleUpdateEmployee();
                    break;
                case 4:
                    handleDeleteEmployee();
                    break;
                case 5:
                    handleSalaryUpdate();
                    break;
                case 6:
                    handleGenerateReports();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    /**
     * Handles employee search functionality.
     */
    @Override
    protected void handleSearchEmployee() {
        System.out.println("\n--- Search Employee ---");
        System.out.println("Search by:");
        System.out.println("1. Name");
        System.out.println("2. SSN");
        System.out.println("3. Employee ID");
        System.out.print("Enter choice: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            String searchType;
            String prompt;

            switch (choice) {
                case 1:
                    searchType = "name";
                    prompt = "Enter name: ";
                    break;
                case 2:
                    searchType = "ssn";
                    prompt = "Enter SSN (9 digits): ";
                    break;
                case 3:
                    searchType = "empid";
                    prompt = "Enter Employee ID: ";
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            System.out.print(prompt);
            String searchTerm = scanner.nextLine().trim();

            List<Employee> results = employeeService.searchEmployee(searchTerm, searchType);

            if (results.isEmpty()) {
                System.out.println("\nNo employees found.");
            } else {
                System.out.println("\n--- Search Results ---");
                displayEmployeeList(results);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }


    /**
     * Handles adding a new employee.
     */
    @Override
    protected void handleAddEmployee() {
        System.out.println("\n--- Add New Employee ---");

        try {
            System.out.print("First Name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine().trim();

            System.out.print("SSN (9 digits): ");
            String ssn = scanner.nextLine().trim();

            System.out.print("Job Title: ");
            String jobTitle = scanner.nextLine().trim();

            System.out.print("Division: ");
            String division = scanner.nextLine().trim();

            System.out.print("Salary: ");
            double salary = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Employment Type (default: FULL_TIME): ");
            String employmentType = scanner.nextLine().trim();
            if (employmentType.isEmpty()) {
                employmentType = "FULL_TIME";
            }

            Employee employee = new Employee(firstName, lastName, ssn, jobTitle, 
                                           division, salary, employmentType);

            employeeService.addEmployee(employee);
            System.out.println("\nEmployee added successfully! Employee ID: " + employee.getEmpId());

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    /**
     * Handles updating an existing employee.
     */
    @Override
    protected void handleUpdateEmployee() {
        System.out.println("\n--- Update Employee ---");

        try {
            System.out.print("Enter Employee ID to update: ");
            int empId = Integer.parseInt(scanner.nextLine().trim());

            Employee employee = employeeService.getEmployeeById(empId);
            if (employee == null) {
                System.out.println("Employee not found.");
                return;
            }

            System.out.println("\nCurrent Employee Information:");
            displayEmployeeDetails(employee);

            System.out.println("\nEnter new values (press Enter to keep current value):");

            System.out.print("First Name [" + employee.getFirstName() + "]: ");
            String firstName = scanner.nextLine().trim();
            if (!firstName.isEmpty()) {
                employee.setFirstName(firstName);
            }

            System.out.print("Last Name [" + employee.getLastName() + "]: ");
            String lastName = scanner.nextLine().trim();
            if (!lastName.isEmpty()) {
                employee.setLastName(lastName);
            }

            System.out.print("SSN [" + employee.getSsn() + "]: ");
            String ssn = scanner.nextLine().trim();
            if (!ssn.isEmpty()) {
                employee.setSsn(ssn);
            }

            System.out.print("Job Title [" + employee.getJobTitle() + "]: ");
            String jobTitle = scanner.nextLine().trim();
            if (!jobTitle.isEmpty()) {
                employee.setJobTitle(jobTitle);
            }

            System.out.print("Division [" + employee.getDivision() + "]: ");
            String division = scanner.nextLine().trim();
            if (!division.isEmpty()) {
                employee.setDivision(division);
            }

            System.out.print("Salary [" + employee.getSalary() + "]: ");
            String salaryStr = scanner.nextLine().trim();
            if (!salaryStr.isEmpty()) {
                employee.setSalary(Double.parseDouble(salaryStr));
            }

            System.out.print("Employment Type [" + employee.getEmploymentType() + "]: ");
            String employmentType = scanner.nextLine().trim();
            if (!employmentType.isEmpty()) {
                employee.setEmploymentType(employmentType);
            }

            employeeService.updateEmployee(employee);
            System.out.println("\nEmployee updated successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }


    /**
     * Handles deleting an employee.
     */
    @Override
    protected void handleDeleteEmployee() {
        System.out.println("\n--- Delete Employee ---");

        try {
            System.out.print("Enter Employee ID to delete: ");
            int empId = Integer.parseInt(scanner.nextLine().trim());

            Employee employee = employeeService.getEmployeeById(empId);
            if (employee == null) {
                System.out.println("Employee not found.");
                return;
            }

            System.out.println("\nEmployee to delete:");
            displayEmployeeDetails(employee);

            System.out.print("\nAre you sure you want to delete this employee? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("yes") || confirmation.equals("y")) {
                employeeService.deleteEmployee(empId);
                System.out.println("\nEmployee deleted successfully!");
            } else {
                System.out.println("\nDeletion cancelled.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    /**
     * Handles salary update by range.
     */
    @Override
    protected void handleSalaryUpdate() {
        System.out.println("\n--- Update Salaries by Range ---");

        try {
            System.out.print("Enter percentage increase (e.g., 3.2 for 3.2%): ");
            double percentage = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter minimum salary (inclusive): ");
            double minSalary = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter maximum salary (exclusive): ");
            double maxSalary = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("\nApply " + percentage + "% increase to salaries >= $" + 
                           minSalary + " and < $" + maxSalary + "? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("yes") || confirmation.equals("y")) {
                int affectedCount = salaryService.applySalaryIncrease(percentage, minSalary, maxSalary);
                System.out.println("\nSalary update completed!");
                System.out.println("Number of employees updated: " + affectedCount);
            } else {
                System.out.println("\nSalary update cancelled.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }


    /**
     * Handles report generation.
     */
    @Override
    protected void handleGenerateReports() {
        System.out.println("\n--- Generate Reports ---");
        System.out.println("1. Full-Time Employee Report with Pay History");
        System.out.println("2. Total Pay by Job Title (Monthly)");
        System.out.println("3. Total Pay by Division (Monthly)");
        System.out.print("Enter choice: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    generateEmployeeReport();
                    break;
                case 2:
                    generatePayByJobTitleReport();
                    break;
                case 3:
                    generatePayByDivisionReport();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    /**
     * Generates and displays employee report.
     */
    private void generateEmployeeReport() {
        try {
            System.out.println("\n--- Full-Time Employee Report ---");
            List<Employee> employees = reportService.generateEmployeeReport();

            if (employees.isEmpty()) {
                System.out.println("No full-time employees found.");
                return;
            }

            for (Employee emp : employees) {
                System.out.println("\n" + "=".repeat(60));
                displayEmployeeDetails(emp);

                List<PayStatement> payStatements = emp.getPayStatements();
                if (payStatements.isEmpty()) {
                    System.out.println("  No pay statements on record.");
                } else {
                    System.out.println("\n  Pay Statement History:");
                    System.out.println("  " + "-".repeat(56));
                    System.out.printf("  %-15s %-20s %15s%n", "Date", "Period", "Amount");
                    System.out.println("  " + "-".repeat(56));
                    for (PayStatement ps : payStatements) {
                        System.out.printf("  %-15s %-20s $%,14.2f%n", 
                                        ps.getPayDate(), ps.getPayPeriod(), ps.getAmount());
                    }
                }
            }
            System.out.println("\n" + "=".repeat(60));
            System.out.println("Total Full-Time Employees: " + employees.size());

        } catch (DatabaseException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    /**
     * Generates and displays pay by job title report.
     */
    private void generatePayByJobTitleReport() {
        try {
            System.out.print("\nEnter month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter year: ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            Map<String, Double> report = reportService.generatePayByJobTitleReport(month, year);

            System.out.println("\n--- Total Pay by Job Title ---");
            System.out.println("Month: " + month + "/" + year);
            System.out.println("=".repeat(60));

            if (report.isEmpty()) {
                System.out.println("No pay data found for this period.");
            } else {
                System.out.printf("%-40s %15s%n", "Job Title", "Total Pay");
                System.out.println("-".repeat(60));
                double grandTotal = 0;
                for (Map.Entry<String, Double> entry : report.entrySet()) {
                    System.out.printf("%-40s $%,14.2f%n", entry.getKey(), entry.getValue());
                    grandTotal += entry.getValue();
                }
                System.out.println("=".repeat(60));
                System.out.printf("%-40s $%,14.2f%n", "GRAND TOTAL", grandTotal);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    /**
     * Generates and displays pay by division report.
     */
    private void generatePayByDivisionReport() {
        try {
            System.out.print("\nEnter month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter year: ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            Map<String, Double> report = reportService.generatePayByDivisionReport(month, year);

            System.out.println("\n--- Total Pay by Division ---");
            System.out.println("Month: " + month + "/" + year);
            System.out.println("=".repeat(60));

            if (report.isEmpty()) {
                System.out.println("No pay data found for this period.");
            } else {
                System.out.printf("%-40s %15s%n", "Division", "Total Pay");
                System.out.println("-".repeat(60));
                double grandTotal = 0;
                for (Map.Entry<String, Double> entry : report.entrySet()) {
                    System.out.printf("%-40s $%,14.2f%n", entry.getKey(), entry.getValue());
                    grandTotal += entry.getValue();
                }
                System.out.println("=".repeat(60));
                System.out.printf("%-40s $%,14.2f%n", "GRAND TOTAL", grandTotal);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }


    /**
     * Displays a list of employees in a formatted table.
     * 
     * @param employees the list of employees to display
     */
    private void displayEmployeeList(List<Employee> employees) {
        System.out.println("=".repeat(120));
        System.out.printf("%-5s %-15s %-15s %-12s %-25s %-20s %12s%n", 
                         "ID", "First Name", "Last Name", "SSN", "Job Title", "Division", "Salary");
        System.out.println("-".repeat(120));
        
        for (Employee emp : employees) {
            System.out.printf("%-5d %-15s %-15s %-12s %-25s %-20s $%,10.2f%n",
                            emp.getEmpId(),
                            emp.getFirstName(),
                            emp.getLastName(),
                            emp.getSsn(),
                            emp.getJobTitle(),
                            emp.getDivision(),
                            emp.getSalary());
        }
        System.out.println("=".repeat(120));
    }

    /**
     * Displays detailed information for a single employee.
     * 
     * @param employee the employee to display
     */
    private void displayEmployeeDetails(Employee employee) {
        System.out.println("  Employee ID:      " + employee.getEmpId());
        System.out.println("  Name:             " + employee.getFullName());
        System.out.println("  SSN:              " + employee.getSsn());
        System.out.println("  Job Title:        " + employee.getJobTitle());
        System.out.println("  Division:         " + employee.getDivision());
        System.out.println("  Salary:           $" + String.format("%,.2f", employee.getSalary()));
        System.out.println("  Employment Type:  " + employee.getEmploymentType());
    }
}
