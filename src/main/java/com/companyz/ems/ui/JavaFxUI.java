package com.companyz.ems.ui;

import java.util.List;

import com.companyz.ems.exception.DatabaseException;
import com.companyz.ems.exception.ValidationException;
import com.companyz.ems.model.Employee;
import com.companyz.ems.service.EmployeeService;
import com.companyz.ems.service.ReportService;
import com.companyz.ems.service.SalaryService;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX-based graphical user interface for the Employee Management System.
 * Provides a modern GUI alternative to the console interface.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class JavaFxUI extends UserInterface {

    private Stage primaryStage;
    private BorderPane mainLayout;
    private TableView<Employee> employeeTable;

    /**
     * Constructor with service dependencies.
     * 
     * @param employeeService the employee service
     * @param reportService the report service
     * @param salaryService the salary service
     */
    public JavaFxUI(EmployeeService employeeService, ReportService reportService, SalaryService salaryService) {
        super(employeeService, reportService, salaryService);
    }

    /**
     * Starts the JavaFX application.
     * Note: This should be called from a JavaFX Application launch.
     */
    @Override
    public void start() {
        // This method is called from JavaFX Application.launch()
        // The actual UI setup is in start(Stage)
    }

    /**
     * JavaFX Application start method.
     * 
     * @param primaryStage the primary stage
     */
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            primaryStage.setTitle("Employee Management System - Company Z");

            mainLayout = new BorderPane();
            mainLayout.setTop(createMenuBar());
            mainLayout.setCenter(createWelcomePane());

            Scene scene = new Scene(mainLayout, 900, 550);
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(500);
            primaryStage.show();
            
            System.out.println("Window displayed successfully!");
        } catch (Exception e) {
            System.err.println("Error creating window: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Displays the main menu (not used in JavaFX, menu bar is used instead).
     */
    @Override
    public void displayMenu() {
        // Menu is displayed via menu bar, not needed here
    }

    /**
     * Creates the menu bar with all options.
     */
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // Employee Menu
        Menu employeeMenu = new Menu("Employee");
        MenuItem searchItem = new MenuItem("Search Employee");
        searchItem.setOnAction(e -> handleSearchEmployee());
        MenuItem addItem = new MenuItem("Add Employee");
        addItem.setOnAction(e -> handleAddEmployee());
        MenuItem updateItem = new MenuItem("Update Employee");
        updateItem.setOnAction(e -> handleUpdateEmployee());
        MenuItem deleteItem = new MenuItem("Delete Employee");
        deleteItem.setOnAction(e -> handleDeleteEmployee());
        employeeMenu.getItems().addAll(searchItem, addItem, updateItem, deleteItem);

        // Salary Menu
        Menu salaryMenu = new Menu("Salary");
        MenuItem salaryUpdateItem = new MenuItem("Update Salaries by Range");
        salaryUpdateItem.setOnAction(e -> handleSalaryUpdate());
        salaryMenu.getItems().add(salaryUpdateItem);

        // Reports Menu
        Menu reportsMenu = new Menu("Reports");
        MenuItem employeeReportItem = new MenuItem("Employee Report");
        employeeReportItem.setOnAction(e -> handleGenerateReports());
        reportsMenu.getItems().add(employeeReportItem);

        // Help Menu
        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> showAboutDialog());
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(employeeMenu, salaryMenu, reportsMenu, helpMenu);
        return menuBar;
    }

    /**
     * Creates the welcome pane.
     */
    private VBox createWelcomePane() {
        VBox welcomePane = new VBox(20);
        welcomePane.setAlignment(Pos.CENTER);
        welcomePane.setPadding(new Insets(50));

        Label titleLabel = new Label("Employee Management System");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label subtitleLabel = new Label("Company Z");
        subtitleLabel.setStyle("-fx-font-size: 18px;");

        Label instructionLabel = new Label("Use the menu bar above to get started");
        instructionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

        welcomePane.getChildren().addAll(titleLabel, subtitleLabel, instructionLabel);
        return welcomePane;
    }

    /**
     * Handles employee search functionality.
     */
    @Override
    protected void handleSearchEmployee() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Search Employee");
        dialog.setHeaderText("Search for an employee");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        ComboBox<String> searchTypeCombo = new ComboBox<>();
        searchTypeCombo.getItems().addAll("Name", "SSN", "Employee ID");
        searchTypeCombo.setValue("Name");

        TextField searchField = new TextField();
        searchField.setPromptText("Enter search term");

        grid.add(new Label("Search by:"), 0, 0);
        grid.add(searchTypeCombo, 1, 0);
        grid.add(new Label("Search term:"), 0, 1);
        grid.add(searchField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String searchType = searchTypeCombo.getValue().toLowerCase().replace(" ", "");
                String searchTerm = searchField.getText();

                try {
                    List<Employee> results = employeeService.searchEmployee(searchTerm, searchType);
                    displayEmployeeResults(results);
                } catch (ValidationException | DatabaseException ex) {
                    showError("Search Error", ex.getMessage());
                }
            }
        });
    }

    /**
     * Displays employee search results in a table.
     */
    private void displayEmployeeResults(List<Employee> employees) {
        VBox resultsPane = new VBox(10);
        resultsPane.setPadding(new Insets(10));

        Label titleLabel = new Label("Search Results (" + employees.size() + " found)");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        employeeTable = new TableView<>();
        employeeTable.setItems(FXCollections.observableArrayList(employees));

        TableColumn<Employee, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("empId"));

        TableColumn<Employee, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Employee, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Employee, String> ssnCol = new TableColumn<>("SSN");
        ssnCol.setCellValueFactory(new PropertyValueFactory<>("ssn"));

        TableColumn<Employee, String> jobTitleCol = new TableColumn<>("Job Title");
        jobTitleCol.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));

        TableColumn<Employee, String> divisionCol = new TableColumn<>("Division");
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        TableColumn<Employee, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        employeeTable.getColumns().addAll(idCol, firstNameCol, lastNameCol, ssnCol, jobTitleCol, divisionCol, salaryCol);

        resultsPane.getChildren().addAll(titleLabel, employeeTable);
        mainLayout.setCenter(resultsPane);
    }

    /**
     * Handles adding a new employee.
     */
    @Override
    protected void handleAddEmployee() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Employee");
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
        ComboBox<String> employmentTypeCombo = new ComboBox<>();
        employmentTypeCombo.getItems().addAll("FULL_TIME", "PART_TIME", "CONTRACT");
        employmentTypeCombo.setValue("FULL_TIME");

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
        grid.add(new Label("Employment Type:"), 0, 6);
        grid.add(employmentTypeCombo, 1, 6);

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
                        employmentTypeCombo.getValue()
                    );

                    employeeService.addEmployee(employee);
                    showInfo("Success", "Employee added successfully! ID: " + employee.getEmpId());
                } catch (NumberFormatException ex) {
                    showError("Input Error", "Salary must be a valid number");
                } catch (ValidationException | DatabaseException ex) {
                    showError("Error", ex.getMessage());
                }
            }
        });
    }

    /**
     * Handles updating an employee.
     */
    @Override
    protected void handleUpdateEmployee() {
        showInfo("Update Employee", "This feature opens a dialog to update employee information.\nUse Search first to find the employee ID.");
    }

    /**
     * Handles deleting an employee.
     */
    @Override
    protected void handleDeleteEmployee() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Employee");
        dialog.setHeaderText("Delete an employee");
        dialog.setContentText("Enter Employee ID:");

        dialog.showAndWait().ifPresent(empIdStr -> {
            try {
                int empId = Integer.parseInt(empIdStr);
                Employee employee = employeeService.getEmployeeById(empId);

                if (employee == null) {
                    showError("Not Found", "Employee not found");
                    return;
                }

                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Delete");
                confirmAlert.setHeaderText("Delete " + employee.getFullName() + "?");
                confirmAlert.setContentText("This action cannot be undone!");

                confirmAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            employeeService.deleteEmployee(empId);
                            showInfo("Success", "Employee deleted successfully");
                        } catch (ValidationException | DatabaseException ex) {
                            showError("Error", ex.getMessage());
                        }
                    }
                });
            } catch (NumberFormatException ex) {
                showError("Input Error", "Employee ID must be a number");
            } catch (ValidationException | DatabaseException ex) {
                showError("Error", ex.getMessage());
            }
        });
    }

    /**
     * Handles salary update by range.
     */
    @Override
    protected void handleSalaryUpdate() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update Salaries by Range");
        dialog.setHeaderText("Apply percentage increase to salary range");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField percentageField = new TextField();
        percentageField.setPromptText("e.g., 3.2");
        TextField minSalaryField = new TextField();
        minSalaryField.setPromptText("e.g., 58000");
        TextField maxSalaryField = new TextField();
        maxSalaryField.setPromptText("e.g., 105000");

        grid.add(new Label("Percentage Increase:"), 0, 0);
        grid.add(percentageField, 1, 0);
        grid.add(new Label("Minimum Salary:"), 0, 1);
        grid.add(minSalaryField, 1, 1);
        grid.add(new Label("Maximum Salary:"), 0, 2);
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
                    showInfo("Success", "Salary update completed!\nEmployees updated: " + count);
                } catch (NumberFormatException ex) {
                    showError("Input Error", "All fields must be valid numbers");
                } catch (ValidationException | DatabaseException ex) {
                    showError("Error", ex.getMessage());
                }
            }
        });
    }

    /**
     * Handles report generation.
     */
    @Override
    protected void handleGenerateReports() {
        try {
            List<Employee> employees = reportService.generateEmployeeReport();
            displayEmployeeResults(employees);
        } catch (DatabaseException ex) {
            showError("Error", ex.getMessage());
        }
    }

    /**
     * Shows an error alert.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows an information alert.
     */
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows the about dialog.
     */
    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Employee Management System");
        alert.setContentText("Version 1.0\nCompany Z Development Team\n\nA simple system for managing employee data.");
        alert.showAndWait();
    }
}
