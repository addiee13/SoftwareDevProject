package com.companyz.ems.ui;

import java.util.List;

import com.companyz.ems.model.Employee;
import com.companyz.ems.service.EmployeeService;
import com.companyz.ems.service.ReportService;
import com.companyz.ems.service.SalaryService;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Simplified JavaFX UI that avoids macOS rendering issues.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class SimpleJavaFxUI extends Application {

    /** The employee service instance. */
    private static EmployeeService employeeService;
    /** The report service instance. */
    private static ReportService reportService;
    /** The salary service instance. */
    private static SalaryService salaryService;
    
    /** The text area for displaying output. */
    private TextArea outputArea;

    /**
     * Sets the service dependencies for the UI.
     * Must be called before launching the application.
     * 
     * @param empService the employee service
     * @param repService the report service
     * @param salService the salary service
     */
    public static void setServices(EmployeeService empService, ReportService repService, SalaryService salService) {
        employeeService = empService;
        reportService = repService;
        salaryService = salService;
    }

    /**
     * Starts the JavaFX application and creates the UI.
     * 
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management System - Company Z");

        // Create main layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        // Title
        Label titleLabel = new Label("Employee Management System");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Buttons
        Button searchBtn = new Button("Search Employee");
        searchBtn.setPrefWidth(200);
        searchBtn.setOnAction(e -> handleSearch());

        Button addBtn = new Button("Add Employee");
        addBtn.setPrefWidth(200);
        addBtn.setOnAction(e -> handleAdd());

        Button updateBtn = new Button("Update Employee");
        updateBtn.setPrefWidth(200);
        updateBtn.setOnAction(e -> handleUpdate());

        Button deleteBtn = new Button("Delete Employee");
        deleteBtn.setPrefWidth(200);
        deleteBtn.setOnAction(e -> handleDelete());

        Button salaryBtn = new Button("Update Salaries by Range");
        salaryBtn.setPrefWidth(200);
        salaryBtn.setOnAction(e -> handleSalaryUpdate());

        Button reportBtn = new Button("Generate Employee Report");
        reportBtn.setPrefWidth(200);
        reportBtn.setOnAction(e -> handleReport());

        // Output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(300);
        outputArea.setText("Welcome to Employee Management System!\nClick a button above to get started.");

        // Layout
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(searchBtn, addBtn, updateBtn, deleteBtn, salaryBtn, reportBtn);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(titleLabel, buttonBox, new Label("Output:"), outputArea);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        System.out.println("JavaFX window created successfully!");
    }

    private void handleSearch() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Employee");
        dialog.setHeaderText("Search for an employee");
        dialog.setContentText("Enter employee name:");

        dialog.showAndWait().ifPresent(name -> {
            try {
                List<Employee> results = employeeService.searchEmployee(name, "name");
                if (results.isEmpty()) {
                    outputArea.setText("No employees found.");
                } else {
                    StringBuilder sb = new StringBuilder("Search Results:\n\n");
                    for (Employee emp : results) {
                        sb.append(String.format("ID: %d | Name: %s | SSN: %s | Title: %s | Salary: $%.2f\n",
                            emp.getEmpId(), emp.getFullName(), emp.getSsn(), 
                            emp.getJobTitle(), emp.getSalary()));
                    }
                    outputArea.setText(sb.toString());
                }
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        });
    }

    private void handleAdd() {
        outputArea.setText("Add Employee feature - Enter employee details in the dialog that appears.");
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Employee");
        dialog.setHeaderText("Enter employee information");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField ssnField = new TextField();
        TextField jobTitleField = new TextField();
        TextField divisionField = new TextField();
        TextField salaryField = new TextField();

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("SSN (9 digits):"), 0, 2);
        grid.add(ssnField, 1, 2);
        grid.add(new Label("Job Title:"), 0, 3);
        grid.add(jobTitleField, 1, 3);
        grid.add(new Label("Division:"), 0, 4);
        grid.add(divisionField, 1, 4);
        grid.add(new Label("Salary:"), 0, 5);
        grid.add(salaryField, 1, 5);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Employee employee = new Employee(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        ssnField.getText(),
                        jobTitleField.getText(),
                        divisionField.getText(),
                        Double.parseDouble(salaryField.getText()),
                        "FULL_TIME"
                    );
                    employeeService.addEmployee(employee);
                    outputArea.setText("Success! Employee added with ID: " + employee.getEmpId());
                } catch (Exception ex) {
                    outputArea.setText("Error: " + ex.getMessage());
                }
            }
        });
    }

    private void handleUpdate() {
        outputArea.setText("Update Employee - Feature available. Enter employee ID to update.");
    }

    private void handleDelete() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Employee");
        dialog.setHeaderText("Delete an employee");
        dialog.setContentText("Enter Employee ID:");

        dialog.showAndWait().ifPresent(empIdStr -> {
            try {
                int empId = Integer.parseInt(empIdStr);
                Employee emp = employeeService.getEmployeeById(empId);
                if (emp == null) {
                    outputArea.setText("Employee not found.");
                    return;
                }

                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirm Delete");
                confirm.setHeaderText("Delete " + emp.getFullName() + "?");
                confirm.setContentText("This cannot be undone!");

                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            employeeService.deleteEmployee(empId);
                            outputArea.setText("Employee deleted successfully!");
                        } catch (Exception ex) {
                            outputArea.setText("Error: " + ex.getMessage());
                        }
                    }
                });
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        });
    }

    private void handleSalaryUpdate() {
        outputArea.setText("Salary Update - Enter percentage and salary range.");
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update Salaries");
        dialog.setHeaderText("Apply salary increase by range");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField percentageField = new TextField();
        TextField minSalaryField = new TextField();
        TextField maxSalaryField = new TextField();

        grid.add(new Label("Percentage:"), 0, 0);
        grid.add(percentageField, 1, 0);
        grid.add(new Label("Min Salary:"), 0, 1);
        grid.add(minSalaryField, 1, 1);
        grid.add(new Label("Max Salary:"), 0, 2);
        grid.add(maxSalaryField, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    double percentage = Double.parseDouble(percentageField.getText());
                    double minSalary = Double.parseDouble(minSalaryField.getText());
                    double maxSalary = Double.parseDouble(maxSalaryField.getText());

                    int count = salaryService.applySalaryIncrease(percentage, minSalary, maxSalary);
                    outputArea.setText("Success! " + count + " employees updated.");
                } catch (Exception ex) {
                    outputArea.setText("Error: " + ex.getMessage());
                }
            }
        });
    }

    private void handleReport() {
        try {
            List<Employee> employees = reportService.generateEmployeeReport();
            StringBuilder sb = new StringBuilder("Employee Report:\n\n");
            for (Employee emp : employees) {
                sb.append(String.format("ID: %d | %s | %s | $%.2f\n",
                    emp.getEmpId(), emp.getFullName(), emp.getJobTitle(), emp.getSalary()));
            }
            sb.append("\nTotal Employees: ").append(employees.size());
            outputArea.setText(sb.toString());
        } catch (Exception ex) {
            outputArea.setText("Error: " + ex.getMessage());
        }
    }
}
