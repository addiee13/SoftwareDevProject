# Design Document

## Overview

The Employee Management System (EMS) is a Java-based application that provides a user interface for managing employee data stored in a MySQL database. The system follows a layered architecture pattern with clear separation between presentation, business logic, and data access layers. The design supports both console and JavaFX GUI implementations through abstraction.

### Key Design Principles

- **Separation of Concerns**: Clear boundaries between UI, business logic, and data access
- **Abstraction**: Use of interfaces and abstract classes to support multiple UI implementations
- **Single Responsibility**: Each class has a focused, well-defined purpose
- **Data Integrity**: Validation at multiple layers to ensure data quality
- **Extensibility**: Design allows for future enhancements without major refactoring

## Architecture

### Layered Architecture

```
┌─────────────────────────────────────────┐
│     Presentation Layer (UX)             │
│  - ConsoleUI / JavaFxUI                 │
│  - Input validation and formatting      │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│     Business Logic Layer                │
│  - EmployeeService                      │
│  - ReportService                        │
│  - SalaryService                        │
│  - Business rules and validation        │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│     Data Access Layer                   │
│  - EmployeeRepository                   │
│  - PayStatementRepository               │
│  - JDBC connection management           │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│     MySQL Database                      │
│  - employeeData database                │
│  - employee, paystatement tables        │
└─────────────────────────────────────────┘
```

### Package Structure

```
com.companyz.ems
├── model
│   ├── Employee.java
│   ├── PayStatement.java
│   └── SalaryRange.java
├── repository
│   ├── Repository.java (interface)
│   ├── EmployeeRepository.java
│   └── PayStatementRepository.java
├── service
│   ├── EmployeeService.java
│   ├── ReportService.java
│   └── SalaryService.java
├── ui
│   ├── UserInterface.java (abstract)
│   ├── ConsoleUI.java
│   └── JavaFxUI.java (optional)
├── util
│   ├── DatabaseConnection.java
│   └── ValidationUtil.java
└── Main.java
```

## Components and Interfaces

### 1. Model Layer

#### Employee Class
```java
public class Employee {
    private int empId;
    private String firstName;
    private String lastName;
    private String ssn;  // 9 digits, no dashes
    private String jobTitle;
    private String division;
    private double salary;
    private List<PayStatement> payStatements;
    
    // Constructor, getters, setters
    // Validation methods
}
```

#### PayStatement Class
```java
public class PayStatement {
    private int statementId;
    private int empId;
    private double amount;
    private LocalDate payDate;
    private String payPeriod;
    
    // Constructor, getters, setters
}
```

#### SalaryRange Class
```java
public class SalaryRange {
    private double minSalary;
    private double maxSalary;
    private double percentageIncrease;
    
    // Constructor, getters, setters
    // Validation methods
}
```

### 2. Repository Layer

#### Repository Interface
```java
public interface Repository<T> {
    T findById(int id);
    List<T> findAll();
    void save(T entity);
    void update(T entity);
    void delete(int id);
}
```

#### EmployeeRepository Class
Implements Repository<Employee> and provides:
- `findByName(String name)`: Search by first or last name
- `findBySsn(String ssn)`: Search by SSN
- `findByEmpId(int empId)`: Search by employee ID
- `findAllFullTime()`: Retrieve all full-time employees
- `updateSalaryByRange(SalaryRange range)`: Bulk salary update
- CRUD operations through Repository interface

#### PayStatementRepository Class
Implements Repository<PayStatement> and provides:
- `findByEmployeeId(int empId)`: Get all pay statements for an employee
- `findByMonthAndYear(int month, int year)`: Get statements for a specific month
- `getTotalPayByJobTitle(int month, int year)`: Aggregate pay by job title
- `getTotalPayByDivision(int month, int year)`: Aggregate pay by division

### 3. Service Layer

#### EmployeeService Class
Business logic for employee operations:
- `searchEmployee(String searchTerm, String searchType)`: Unified search interface
- `addEmployee(Employee employee)`: Validate and insert new employee
- `updateEmployee(Employee employee)`: Validate and update employee data
- `deleteEmployee(int empId)`: Remove employee record
- Input validation and business rule enforcement

#### ReportService Class
Report generation logic:
- `generateEmployeeReport()`: Full-time employees with pay history
- `generatePayByJobTitleReport(int month, int year)`: Monthly pay by job title
- `generatePayByDivisionReport(int month, int year)`: Monthly pay by division
- Format data for presentation layer

#### SalaryService Class
Salary management logic:
- `applySalaryIncrease(double percentage, double minSalary, double maxSalary)`: Apply percentage increase to salary range
- Validate salary range parameters
- Calculate and return affected employee count

### 4. Presentation Layer

#### UserInterface Abstract Class
```java
public abstract class UserInterface {
    protected EmployeeService employeeService;
    protected ReportService reportService;
    protected SalaryService salaryService;
    
    public abstract void start();
    public abstract void displayMenu();
    protected abstract void handleSearchEmployee();
    protected abstract void handleAddEmployee();
    protected abstract void handleUpdateEmployee();
    protected abstract void handleDeleteEmployee();
    protected abstract void handleSalaryUpdate();
    protected abstract void handleGenerateReports();
}
```

#### ConsoleUI Class
Extends UserInterface and implements:
- Text-based menu system
- Console input/output using Scanner
- Formatted text output for reports
- Error message display

#### JavaFxUI Class (Optional)
Extends UserInterface and implements:
- JavaFX Stage and Scene setup
- Form-based input controls
- TableView for displaying employee lists and reports
- Alert dialogs for confirmations and errors

### 5. Utility Layer

#### DatabaseConnection Class
Singleton pattern for database connection management:
- `getInstance()`: Get singleton instance
- `getConnection()`: Obtain JDBC connection from pool
- `closeConnection(Connection conn)`: Properly close connections
- Connection string configuration
- Error handling for connection failures

#### ValidationUtil Class
Static utility methods for data validation:
- `isValidSSN(String ssn)`: Validate 9-digit SSN format
- `isValidSalary(double salary)`: Validate salary is positive
- `isValidPercentage(double percentage)`: Validate percentage is positive
- `isNotEmpty(String value)`: Validate required string fields
- `stripSSNFormatting(String ssn)`: Remove any dashes or spaces from SSN input to store as 9 digits

## Data Models

### Database Schema

#### employee Table
```sql
CREATE TABLE employee (
    empId INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    ssn VARCHAR(9) NOT NULL UNIQUE,
    jobTitle VARCHAR(100) NOT NULL,
    division VARCHAR(100) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    employmentType VARCHAR(20) DEFAULT 'FULL_TIME'
);
```

#### paystatement Table
```sql
CREATE TABLE paystatement (
    statementId INT PRIMARY KEY AUTO_INCREMENT,
    empId INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payDate DATE NOT NULL,
    payPeriod VARCHAR(20) NOT NULL,
    FOREIGN KEY (empId) REFERENCES employee(empId) ON DELETE CASCADE
);
```

### Data Flow

1. **Search Employee Flow**
   - User enters search criteria in UI
   - UI calls EmployeeService.searchEmployee()
   - Service validates input and calls appropriate EmployeeRepository method
   - Repository executes SQL query via JDBC
   - Results flow back through layers to UI for display

2. **Update Employee Flow**
   - User selects employee and modifies data in UI
   - UI creates Employee object and calls EmployeeService.updateEmployee()
   - Service validates business rules
   - Service calls EmployeeRepository.update()
   - Repository executes UPDATE SQL statement
   - Confirmation flows back to UI

3. **Salary Update Flow**
   - User enters percentage and salary range in UI
   - UI calls SalaryService.applySalaryIncrease()
   - Service validates parameters and creates SalaryRange object
   - Service calls EmployeeRepository.updateSalaryByRange()
   - Repository executes UPDATE with WHERE clause for salary range
   - Count of affected rows returned to UI

4. **Report Generation Flow**
   - User selects report type and enters parameters in UI
   - UI calls appropriate ReportService method
   - Service calls repository methods to fetch data
   - Repository executes SELECT queries with JOINs and aggregations
   - Service formats data into report structure
   - UI displays formatted report

## Error Handling

### Exception Hierarchy

```
Exception
└── EMSException (custom base exception)
    ├── DatabaseException (connection, SQL errors)
    ├── ValidationException (invalid input data)
    └── NotFoundException (entity not found)
```

### Error Handling Strategy

1. **Repository Layer**: Catch SQLException, wrap in DatabaseException with meaningful message
2. **Service Layer**: Validate inputs, throw ValidationException for invalid data
3. **UI Layer**: Catch all exceptions, display user-friendly error messages
4. **Logging**: Log all exceptions with stack traces for debugging

### Specific Error Scenarios

- **Database Connection Failure**: Display error message, prevent application from starting
- **Invalid SSN Format**: Show validation error, prompt user to re-enter
- **Employee Not Found**: Display "No results found" message
- **Duplicate SSN**: Catch unique constraint violation, show error message
- **SQL Execution Error**: Log error, display generic database error message to user

## Testing Strategy

### Unit Testing

**Target Classes for Unit Tests:**
- ValidationUtil: Test all validation methods with valid and invalid inputs
- Employee/PayStatement models: Test getters, setters, and validation methods
- SalaryRange: Test calculation logic and validation

**Testing Framework:** JUnit 5

**Approach:**
- Test each validation method with boundary values
- Test model object creation and data integrity
- Mock dependencies where needed

### Integration Testing

**Target Components:**
- Repository classes with actual database connection
- Service layer calling repository methods
- End-to-end data flow from service to database

**Test Database:**
- Use separate test database (employeeData_test)
- Populate with known test data before each test
- Clean up after each test

**Test Scenarios:**
- Insert employee and verify in database
- Search employee by different criteria
- Update employee salary and verify changes
- Generate reports and verify data accuracy
- Bulk salary update and verify affected records

### System Testing

**Test Cases:**

1. **Search Employee Test**
   - Input: Valid employee name
   - Expected: Display matching employee(s) with all fields
   - Pass/Fail: Verify correct employee data displayed

2. **Update Employee Data Test**
   - Input: Modified employee information
   - Expected: Database updated, confirmation message shown
   - Pass/Fail: Verify database contains updated values

3. **Salary Update by Range Test**
   - Input: 3.2% increase for salary >= 58000 and < 105000
   - Expected: Only employees in range updated, count displayed
   - Pass/Fail: Verify correct employees updated with correct new salary

4. **Generate Employee Report Test**
   - Input: Request full-time employee report
   - Expected: Display all full-time employees with pay history
   - Pass/Fail: Verify all employees shown with correct pay statements

5. **Generate Pay by Job Title Report Test**
   - Input: Month and year
   - Expected: Display total pay grouped by job title
   - Pass/Fail: Verify totals match database aggregation

### Boundary and Negative Test Cases

**Validation Tests:**
- Invalid SSN format (less than 9 digits, contains letters, contains dashes)
- Negative salary values
- Zero or negative percentage for salary increase
- Empty required fields (name, job title, division)
- Salary range where min > max
- Non-existent employee ID for update/delete
- Invalid date formats for pay statements
- SQL injection attempts in search fields

**Edge Cases:**
- Search with no results
- Update salary range affecting zero employees
- Employee with no pay statements
- Report for month with no pay data
- Maximum salary value (boundary testing)
- Concurrent updates to same employee record

**UI Interaction Tests (JavaFX):**
- Form validation before submission
- Proper error message display
- Table sorting and filtering
- Navigation between views
- Cancel operations without saving

### UML Sequence Diagrams for Testing

**Sequence Diagram 1: Overall System Interaction**
Shows the flow from user action through UI → Service → Repository → Database and back

**Sequence Diagram 2: Report Generation**
Shows the specific flow for generating reports including data aggregation

## Implementation Notes

### JDBC Connection Configuration

```java
// DatabaseConnection.java configuration
private static final String URL = "jdbc:mysql://localhost:3306/employeeData";
private static final String USER = "root";
private static final String PASSWORD = "password";
private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
```

### Maven Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>17.0.2</version>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.9.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### Design Patterns Used

1. **Singleton Pattern**: DatabaseConnection for single connection pool instance
2. **Repository Pattern**: Abstraction of data access logic
3. **Service Layer Pattern**: Business logic separation
4. **Template Method Pattern**: UserInterface abstract class with concrete implementations
5. **Factory Pattern** (optional): For creating different UI implementations

### UML Diagrams Required

1. **Use Case Diagram - Overall System**: Shows Administrator actor interacting with all system features (search, insert, update, delete, reports)
2. **Use Case Diagram - Reporting**: Focused view of report generation use cases (employee report, pay by job title, pay by division)
3. **Class Diagram**: Complete class structure showing all classes, relationships, inheritance, interfaces, and key methods with attributes
4. **Sequence Diagram - Overall System**: Shows typical CRUD operation flow (e.g., search employee or insert employee)
5. **Sequence Diagram - Reporting**: Shows report generation flow with data aggregation and formatting
6. **Sequence Diagram - Update Employee**: Shows update operation with validation and transaction management
7. **Sequence Diagram - Salary Update by Range**: Shows bulk salary update with transaction control and affected row count
8. **Sequence Diagram - Delete Employee**: Shows deletion with cascading pay statement removal

## Concurrency and Transactions

### Transaction Management

**Critical Operations Requiring Transactions:**
- Salary updates affecting multiple employees
- Employee deletion with cascading pay statement removal
- Bulk data import operations

**Implementation Approach:**
```java
Connection conn = null;
try {
    conn = DatabaseConnection.getInstance().getConnection();
    conn.setAutoCommit(false);
    
    // Perform multiple operations
    employeeRepository.updateSalaryByRange(range, conn);
    
    conn.commit();
} catch (SQLException e) {
    if (conn != null) {
        conn.rollback();
    }
    throw new DatabaseException("Transaction failed", e);
} finally {
    if (conn != null) {
        conn.setAutoCommit(true);
        conn.close();
    }
}
```

### Concurrency Control

**Synchronization Strategy:**
- DatabaseConnection uses synchronized methods for connection pool access
- Repository methods use database-level locking for concurrent updates
- Optimistic locking for employee updates (check last modified timestamp)

**Concurrent Access Scenarios:**
- Multiple administrators updating different employees: No conflict
- Multiple administrators updating same employee: Last write wins with warning
- Salary update during employee modification: Transaction isolation prevents inconsistency

## Configuration Management

### Externalized Configuration

**database.properties File:**
```properties
db.url=jdbc:mysql://localhost:3306/employeeData
db.username=root
db.password=
db.driver=com.mysql.cj.jdbc.Driver
db.pool.size=10
db.connection.timeout=30000
```

**Configuration Loading:**
```java
public class ConfigurationManager {
    private static Properties properties;
    
    static {
        properties = new Properties();
        try (InputStream input = ConfigurationManager.class
                .getClassLoader()
                .getResourceAsStream("database.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
```

**Environment Variable Support:**
- Override properties with environment variables for deployment
- Support for DB_URL, DB_USERNAME, DB_PASSWORD environment variables

## Performance Considerations

- Use PreparedStatement for all SQL queries to prevent SQL injection and improve performance
- Implement connection pooling for better database connection management
- Lazy load pay statements only when needed for reports
- Index SSN and empId columns for faster searches
- Limit result sets for large data queries
- Use batch updates for bulk salary modifications
- Cache frequently accessed reference data (job titles, divisions)

## Security Considerations

- Validate all user inputs to prevent SQL injection
- Use PreparedStatement with parameterized queries
- Display SSN as 9 consecutive digits per requirement (no masking or dashes)
- Store database credentials in external configuration, not in code
- No authentication required per project specifications
- Log all data modification operations for audit trail
- Implement input sanitization for all user-provided data
- Strip any formatting from SSN input before storage to ensure 9-digit format

## Documentation Requirements

### JavaDoc Standards

**All classes must include:**
- Class-level JavaDoc describing purpose and responsibilities
- Author and version tags
- Example usage where appropriate

**All public methods must include:**
- Method description
- @param tags for all parameters
- @return tag for non-void methods
- @throws tags for declared exceptions

**Example:**
```java
/**
 * Service class for managing employee-related business operations.
 * Provides methods for searching, adding, updating, and deleting employees
 * with validation and error handling.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class EmployeeService {
    
    /**
     * Searches for employees based on the provided search criteria.
     * 
     * @param searchTerm the value to search for (name, SSN, or employee ID)
     * @param searchType the type of search ("name", "ssn", or "empid")
     * @return list of matching employees, empty list if no matches found
     * @throws ValidationException if searchTerm is empty or searchType is invalid
     * @throws DatabaseException if database error occurs during search
     */
    public List<Employee> searchEmployee(String searchTerm, String searchType) {
        // Implementation
    }
}
```

### User Documentation

**Quick Start Guide Contents:**
1. System requirements (Java version, MySQL version)
2. Database setup instructions (schema creation, sample data)
3. Configuration file setup (database.properties)
4. How to compile and run the application
5. Main menu navigation guide
6. Step-by-step instructions for each feature
7. Troubleshooting common issues

**Software Design Document (SWDD):**
- Follow provided SWDD template from iCollege
- Include all UML diagrams
- Document all design decisions and rationales
- Include test cases and results
- Add screenshots of working system

## Future Enhancements

### Immediate Improvements
- Export reports to PDF using iText library
- Export reports to CSV for Excel compatibility
- Add report totals, averages, and summary statistics
- Implement report date range filtering
- Add employee count and salary statistics to reports

### Extended Features
- User authentication and role-based access control
- Email notification for salary updates
- Employee photo upload and display
- Advanced search with multiple criteria (AND/OR conditions)
- Pagination for large employee lists
- Employee history tracking (audit log of changes)
- Dashboard with key metrics and charts
- RESTful API for web-based access
- Mobile application interface
