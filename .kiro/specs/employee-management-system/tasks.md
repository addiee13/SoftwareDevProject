# Implementation Plan

- [x] 1. Set up project structure and configuration
  - Create Maven project with standard directory structure (src/main/java, src/main/resources, src/test/java)
  - Create package structure: model, repository, service, ui, util
  - Add Maven dependencies for MySQL Connector, JUnit 5, and JavaFX (optional)
  - Create database.properties file in resources folder with JDBC configuration
  - _Requirements: 8.1, 8.2_

- [x] 2. Create database schema and sample data
  - Write SQL script to create employee table with empId, firstName, lastName, ssn, jobTitle, division, salary, employmentType columns
  - Write SQL script to create paystatement table with statementId, empId, amount, payDate, payPeriod columns
  - Add foreign key constraint from paystatement.empId to employee.empId with CASCADE delete
  - Create indexes on ssn and empId columns for search performance
  - Insert sample data for 15-20 employees with various job titles and divisions
  - Insert sample pay statement records for testing reports
  - _Requirements: 1.1, 5.1, 5.3, 6.1, 7.1_

- [x] 3. Implement model classes
  - [x] 3.1 Create Employee class with all fields and validation
    - Define private fields: empId, firstName, lastName, ssn, jobTitle, division, salary, employmentType
    - Implement constructor, getters, and setters
    - Add List<PayStatement> field for pay history
    - Add JavaDoc comments for class and all methods
    - _Requirements: 1.1, 1.2, 2.4, 3.1, 3.2_
  
  - [x] 3.2 Create PayStatement class
    - Define private fields: statementId, empId, amount, payDate, payPeriod
    - Implement constructor, getters, and setters
    - Add JavaDoc comments
    - _Requirements: 5.3, 6.2, 7.2_
  
  - [x] 3.3 Create SalaryRange class
    - Define fields: minSalary, maxSalary, percentageIncrease
    - Implement constructor, getters, and setters
    - Add validation method to ensure minSalary < maxSalary and percentage > 0
    - Add JavaDoc comments
    - _Requirements: 4.1, 4.2, 4.3_

- [x] 4. Implement utility classes
  - [x] 4.1 Create ValidationUtil class
    - Implement isValidSSN() method to check for exactly 9 digits
    - Implement isValidSalary() method to check for positive values
    - Implement isValidPercentage() method to check for positive values
    - Implement isNotEmpty() method for required string fields
    - Implement stripSSNFormatting() to remove dashes/spaces from input
    - Add JavaDoc comments for all methods
    - _Requirements: 1.3, 3.4, 4.5_
  
  - [x] 4.2 Create ConfigurationManager class
    - Implement static method to load database.properties file
    - Implement getProperty() method to retrieve configuration values
    - Add support for environment variable overrides
    - Handle IOException if properties file not found
    - Add JavaDoc comments
    - _Requirements: 8.1_
  
  - [x] 4.3 Create DatabaseConnection singleton class
    - Implement getInstance() method for singleton pattern
    - Implement getConnection() method to create JDBC connection using ConfigurationManager
    - Implement closeConnection() method to properly close connections
    - Add synchronized methods for thread safety
    - Handle SQLException and wrap in DatabaseException
    - Add JavaDoc comments
    - _Requirements: 8.1, 8.2, 8.3, 8.4_

- [x] 5. Create custom exception classes
  - Create EMSException as base exception class extending Exception
  - Create DatabaseException extending EMSException for database errors
  - Create ValidationException extending EMSException for validation errors
  - Create NotFoundException extending EMSException for entity not found errors
  - Add constructors accepting message and cause
  - Add JavaDoc comments for all exception classes
  - _Requirements: 3.5, 8.3_

- [x] 6. Implement repository layer
  - [x] 6.1 Create Repository interface
    - Define generic interface Repository<T>
    - Declare methods: findById(), findAll(), save(), update(), delete()
    - Add JavaDoc comments describing contract
    - _Requirements: 8.2_
  
  - [x] 6.2 Implement EmployeeRepository class
    - Implement Repository<Employee> interface
    - Implement findById() using PreparedStatement with SELECT WHERE empId
    - Implement findAll() to retrieve all employees
    - Implement save() to INSERT new employee record
    - Implement update() to UPDATE employee record with transaction support
    - Implement delete() to DELETE employee by empId
    - Implement findByName() to search by firstName or lastName using LIKE
    - Implement findBySsn() to search by exact SSN match
    - Implement findByEmpId() as alias for findById()
    - Implement findAllFullTime() to get employees where employmentType = 'FULL_TIME'
    - Implement updateSalaryByRange() to bulk update salaries with WHERE clause for salary range
    - Use PreparedStatement for all queries to prevent SQL injection
    - Handle SQLException and wrap in DatabaseException
    - Add JavaDoc comments for all methods
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 3.1, 3.2, 3.3, 4.3, 5.1, 8.2_
  
  - [x] 6.3 Implement PayStatementRepository class
    - Implement Repository<PayStatement> interface
    - Implement findById() to get pay statement by statementId
    - Implement findAll() to retrieve all pay statements
    - Implement save() to INSERT new pay statement
    - Implement update() to UPDATE pay statement
    - Implement delete() to DELETE pay statement
    - Implement findByEmployeeId() to get all pay statements for an employee with JOIN
    - Implement findByMonthAndYear() to filter pay statements by payDate
    - Implement getTotalPayByJobTitle() using GROUP BY jobTitle with JOIN to employee table
    - Implement getTotalPayByDivision() using GROUP BY division with JOIN to employee table
    - Use PreparedStatement for all queries
    - Handle SQLException and wrap in DatabaseException
    - Add JavaDoc comments for all methods
    - _Requirements: 5.3, 6.1, 6.2, 6.3, 7.1, 7.2, 7.3, 8.2_

- [x] 7. Implement service layer
  - [x] 7.1 Create EmployeeService class
    - Inject EmployeeRepository dependency in constructor
    - Implement searchEmployee() method accepting searchTerm and searchType parameters
    - Route to appropriate repository method based on searchType (name, ssn, empid)
    - Implement addEmployee() method with validation using ValidationUtil
    - Validate required fields are not empty before calling repository.save()
    - Implement updateEmployee() method with validation
    - Implement deleteEmployee() method
    - Throw ValidationException for invalid inputs
    - Catch DatabaseException and rethrow with context
    - Add JavaDoc comments for all methods
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 3.1, 3.2, 3.3, 3.4, 3.5_
  
  - [x] 7.2 Create ReportService class
    - Inject EmployeeRepository and PayStatementRepository dependencies
    - Implement generateEmployeeReport() to get full-time employees with pay history
    - Call employeeRepository.findAllFullTime() and payStatementRepository.findByEmployeeId() for each
    - Format data into readable report structure
    - Implement generatePayByJobTitleReport() accepting month and year parameters
    - Call payStatementRepository.getTotalPayByJobTitle() and format results
    - Implement generatePayByDivisionReport() accepting month and year parameters
    - Call payStatementRepository.getTotalPayByDivision() and format results
    - Add JavaDoc comments for all methods
    - _Requirements: 5.1, 5.2, 5.3, 5.4, 6.1, 6.2, 6.3, 6.4, 7.1, 7.2, 7.3, 7.4_
  
  - [x] 7.3 Create SalaryService class
    - Inject EmployeeRepository dependency
    - Implement applySalaryIncrease() accepting percentage, minSalary, maxSalary parameters
    - Validate parameters using ValidationUtil (positive percentage, minSalary < maxSalary)
    - Create SalaryRange object
    - Call employeeRepository.updateSalaryByRange() with transaction support
    - Return count of affected employees
    - Throw ValidationException for invalid parameters
    - Add JavaDoc comments for all methods
    - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5_

- [x] 8. Implement presentation layer
  - [x] 8.1 Create UserInterface abstract class
    - Define protected fields for EmployeeService, ReportService, SalaryService
    - Define abstract methods: start(), displayMenu(), handleSearchEmployee(), handleAddEmployee(), handleUpdateEmployee(), handleDeleteEmployee(), handleSalaryUpdate(), handleGenerateReports()
    - Add constructor accepting service dependencies
    - Add JavaDoc comments
    - _Requirements: 9.1, 9.2_
  
  - [x] 8.2 Implement ConsoleUI class
    - Extend UserInterface abstract class
    - Implement start() method to initialize Scanner and call displayMenu()
    - Implement displayMenu() to show numbered menu options for all features
    - Implement handleSearchEmployee() to prompt for search type and term, call employeeService.searchEmployee(), display results
    - Implement handleAddEmployee() to prompt for all employee fields, validate, call employeeService.addEmployee()
    - Implement handleUpdateEmployee() to prompt for empId, load employee, prompt for fields to update, call employeeService.updateEmployee()
    - Implement handleDeleteEmployee() to prompt for empId, confirm, call employeeService.deleteEmployee()
    - Implement handleSalaryUpdate() to prompt for percentage and salary range, call salaryService.applySalaryIncrease(), display count
    - Implement handleGenerateReports() to show report submenu, call appropriate reportService methods, format and display results
    - Display user-friendly error messages for all exceptions
    - Implement loop to return to main menu after each operation
    - Add JavaDoc comments for all methods
    - _Requirements: 2.5, 3.5, 9.1, 9.2, 9.3, 9.4, 9.5_

- [x] 9. Create Main class and application entry point
  - Create Main class with main() method
  - Initialize DatabaseConnection to verify database connectivity
  - Create repository instances (EmployeeRepository, PayStatementRepository)
  - Create service instances with repository dependencies
  - Create ConsoleUI instance with service dependencies
  - Call ui.start() to launch application
  - Catch and display any startup errors
  - Add JavaDoc comments
  - _Requirements: 8.1, 8.3, 9.1_

- [x] 10. Implement transaction management for critical operations
  - Update EmployeeRepository.updateSalaryByRange() to accept Connection parameter
  - Wrap salary update in transaction with setAutoCommit(false), commit(), and rollback() on error
  - Update EmployeeRepository.delete() to use transaction for cascading deletes
  - Add transaction support to SalaryService.applySalaryIncrease()
  - Test rollback behavior on errors
  - _Requirements: 4.3, 4.4_

- [x] 11. Write unit tests for validation and models
  - [x] 11.1 Create ValidationUtilTest class
    - Test isValidSSN() with valid 9-digit SSN (pass)
    - Test isValidSSN() with invalid SSN: 8 digits, 10 digits, contains letters, contains dashes (fail)
    - Test isValidSalary() with positive value (pass)
    - Test isValidSalary() with zero and negative values (fail)
    - Test isValidPercentage() with positive value (pass)
    - Test isValidPercentage() with zero and negative values (fail)
    - Test isNotEmpty() with non-empty string (pass)
    - Test isNotEmpty() with empty string and null (fail)
    - Test stripSSNFormatting() removes dashes and spaces
    - _Requirements: 1.3, 3.4, 4.5_
  
  - [x] 11.2 Create EmployeeTest class
    - Test Employee object creation with valid data
    - Test getter and setter methods
    - Test SSN validation in Employee class
    - _Requirements: 1.1, 1.2, 3.1_
  
  - [x] 11.3 Create SalaryRangeTest class
    - Test SalaryRange validation with valid range (minSalary < maxSalary)
    - Test SalaryRange validation with invalid range (minSalary >= maxSalary)
    - Test percentage validation
    - _Requirements: 4.1, 4.2, 4.5_

- [x] 12. Write integration tests with test database
  - [x] 12.1 Set up test database
    - Create employeeData_test database
    - Create test schema matching production
    - Create test data setup and teardown methods
    - Configure test database.properties
    - _Requirements: 8.1_
  
  - [x] 12.2 Create EmployeeRepositoryTest class
    - Test save() inserts employee and returns generated empId
    - Test findById() retrieves correct employee
    - Test findByName() returns matching employees
    - Test findBySsn() returns correct employee
    - Test update() modifies employee data
    - Test delete() removes employee
    - Test updateSalaryByRange() updates only employees in range
    - Verify affected row counts
    - _Requirements: 2.1, 2.2, 2.3, 3.1, 3.2, 3.3, 4.3_
  
  - [x] 12.3 Create PayStatementRepositoryTest class
    - Test findByEmployeeId() returns all pay statements for employee
    - Test getTotalPayByJobTitle() returns correct aggregations
    - Test getTotalPayByDivision() returns correct aggregations
    - Verify calculations match expected totals
    - _Requirements: 5.3, 6.2, 6.3, 7.2, 7.3_
  
  - [x] 12.4 Create service layer integration tests
    - Test EmployeeService.searchEmployee() with various search types
    - Test EmployeeService.addEmployee() with validation
    - Test SalaryService.applySalaryIncrease() with transaction rollback
    - Test ReportService methods return correct data
    - _Requirements: 2.4, 3.4, 4.3, 5.4, 6.4, 7.4_

- [x] 13. Create system test cases document
  - Document Test Case 1: Search Employee (by name, SSN, empId) with expected results and pass/fail criteria
  - Document Test Case 2: Update Employee Data with before/after database verification
  - Document Test Case 3: Salary Update by Range (3.2% for salary >= 58000 and < 105000) with verification of affected employees
  - Include test data setup instructions
  - Include expected vs actual results format
  - Add screenshots or console output examples
  - _Requirements: 2.1, 2.2, 2.3, 2.4, 3.2, 4.1, 4.2, 4.3, 4.4_

- [ ]* 14. Implement JavaFX GUI (optional alternative to console)
  - [ ]* 14.1 Create JavaFxUI class extending UserInterface
    - Set up JavaFX Application with Stage and Scene
    - Create main menu with buttons for each feature
    - Implement handleSearchEmployee() with form inputs and TableView for results
    - Implement handleAddEmployee() with form fields and validation
    - Implement handleUpdateEmployee() with employee selection and edit form
    - Implement handleDeleteEmployee() with confirmation dialog
    - Implement handleSalaryUpdate() with input fields and result display
    - Implement handleGenerateReports() with report selection and TableView display
    - Add error Alert dialogs for exceptions
    - Style UI with CSS for professional appearance
    - _Requirements: 9.1, 9.2, 9.3, 9.4, 9.5_
  
  - [ ]* 14.2 Update Main class to support UI selection
    - Add command-line argument or configuration to choose between Console and JavaFX UI
    - Instantiate appropriate UI implementation
    - _Requirements: 9.1_

- [ ] 15. Create UML diagrams
  - [ ] 15.1 Create Use Case Diagram - Overall System
    - Show Administrator actor
    - Show use cases: Search Employee, Add Employee, Update Employee, Delete Employee, Update Salary by Range, Generate Reports
    - Show relationships between use cases
    - _Requirements: All_
  
  - [ ] 15.2 Create Use Case Diagram - Reporting
    - Show Administrator actor
    - Show use cases: Generate Employee Report, Generate Pay by Job Title Report, Generate Pay by Division Report
    - Show include relationships if applicable
    - _Requirements: 5.1, 6.1, 7.1_
  
  - [ ] 15.3 Create Class Diagram
    - Show all classes: model, repository, service, ui, util packages
    - Show inheritance relationships (UserInterface → ConsoleUI/JavaFxUI, Repository → concrete repositories)
    - Show associations between classes (services use repositories, UI uses services)
    - Include key attributes and methods for each class
    - Show interfaces and abstract classes with proper notation
    - _Requirements: All_
  
  - [ ] 15.4 Create Sequence Diagram - Overall System (Search Employee)
    - Show interaction: User → ConsoleUI → EmployeeService → EmployeeRepository → Database
    - Show method calls and return values
    - Show database query execution
    - _Requirements: 2.1, 2.2, 2.3, 2.4_
  
  - [ ] 15.5 Create Sequence Diagram - Reporting
    - Show interaction: User → ConsoleUI → ReportService → PayStatementRepository → Database
    - Show data aggregation and formatting
    - Show return of formatted report
    - _Requirements: 5.1, 6.1, 7.1_
  
  - [ ] 15.6 Create Sequence Diagram - Update Employee
    - Show validation flow and transaction management
    - Show rollback on error scenario
    - _Requirements: 3.2, 3.4_
  
  - [ ] 15.7 Create Sequence Diagram - Salary Update by Range
    - Show bulk update with transaction control
    - Show affected row count return
    - _Requirements: 4.1, 4.2, 4.3, 4.4_
  
  - [ ] 15.8 Create Sequence Diagram - Delete Employee
    - Show cascading delete of pay statements
    - Show transaction management
    - _Requirements: 3.3_

- [ ] 16. Create comprehensive documentation
  - [ ] 16.1 Write JavaDoc for all classes and methods
    - Ensure all classes have class-level JavaDoc with description, author, version
    - Ensure all public methods have JavaDoc with @param, @return, @throws tags
    - Generate JavaDoc HTML using Maven javadoc plugin
    - _Requirements: All_
  
  - [ ] 16.2 Create Quick Start Guide
    - Document system requirements (Java 11+, MySQL 8.0+, Maven 3.6+)
    - Provide database setup instructions with SQL scripts
    - Explain database.properties configuration
    - Provide compilation instructions (mvn clean compile)
    - Provide run instructions (mvn exec:java or java -jar)
    - Document each feature with step-by-step usage
    - Add troubleshooting section for common issues
    - _Requirements: 8.1, 9.1_
  
  - [ ] 16.3 Complete Software Design Document (SWDD)
    - Follow SWDD template from iCollege
    - Include all UML diagrams
    - Document design decisions and rationales
    - Include test cases and results
    - Add screenshots of working system
    - Export to PDF format
    - _Requirements: All_

- [ ] 17. Prepare final deliverables
  - [ ] 17.1 Test complete system end-to-end
    - Verify all features work correctly
    - Test with sample data matching project scenario
    - Verify all reports generate accurate data
    - Test error handling and edge cases
    - _Requirements: All_
  
  - [ ] 17.2 Create demonstration video
    - Record 7-15 minute video demonstration
    - Show system startup and main menu
    - Demonstrate search functionality with different criteria
    - Demonstrate adding new employee
    - Demonstrate updating employee data
    - Demonstrate salary update by range with verification
    - Demonstrate all three reports
    - Show database verification of changes
    - Narrate actions and explain functionality
    - Export in compatible format (MPEG-4, MP4, MOV)
    - _Requirements: All_
  
  - [ ] 17.3 Package final submission
    - Create zip file with all source code
    - Include database SQL scripts
    - Include configuration files
    - Include compiled JAR file
    - Include all UML diagrams
    - Include SWDD PDF
    - Include demonstration video
    - Verify all files are included and accessible
    - _Requirements: All_
