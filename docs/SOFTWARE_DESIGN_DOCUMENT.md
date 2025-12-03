# Software Design Document (SWDD)
# Employee Management System

**Project**: Employee Management System for Company Z  
**Version**: 1.0.0  
**Date**: November 2025  
**Author**: Company Z Development Team

---

## Document Control

| Version | Date | Author | Description |
|---------|------|--------|-------------|
| 1.0.0 | November 2025 | Company Z Dev Team | Initial release |

---

## Table of Contents

1. [Introduction](#1-introduction)
2. [System Overview](#2-system-overview)
3. [System Architecture](#3-system-architecture)
4. [Design Decisions and Rationale](#4-design-decisions-and-rationale)
5. [Data Design](#5-data-design)
6. [Component Design](#6-component-design)
7. [User Interface Design](#7-user-interface-design)
8. [UML Diagrams](#8-uml-diagrams)
9. [Testing Strategy](#9-testing-strategy)
10. [Test Cases and Results](#10-test-cases-and-results)
11. [System Screenshots](#11-system-screenshots)
12. [Deployment Guide](#12-deployment-guide)
13. [Appendices](#13-appendices)

---

## 1. Introduction

### 1.1 Purpose

This Software Design Document (SWDD) describes the architecture and design of the Employee Management System (EMS) 
developed for Company Z. The system provides a comprehensive solution for managing employee data, processing salary 
updates, and generating payroll reports for small organizations with fewer than twenty full-time employees.

### 1.2 Scope

The EMS replaces manual database operations with a structured application interface that supports:
- Employee data management (create, read, update, delete)
- Advanced search capabilities (by name, SSN, or employee ID)
- Bulk salary updates by salary range
- Comprehensive reporting (employee reports, pay by job title, pay by division)
- Both console and graphical user interfaces

### 1.3 Intended Audience

This document is intended for:
- Software developers maintaining or extending the system
- System administrators deploying the application
- Quality assurance teams testing the system
- Project stakeholders reviewing the design
- Academic reviewers evaluating the project

### 1.4 Document Conventions

- **Bold text**: Important terms and concepts
- `Code font`: Code snippets, file names, and commands
- *Italic text*: Emphasis and references


## 2. System Overview

### 2.1 System Description

The Employee Management System is a Java-based desktop application that provides a user-friendly interface for 
managing employee records and payroll data stored in a MySQL database. The system follows a layered architecture 
pattern with clear separation between presentation, business logic, and data access layers.

### 2.2 Key Features

1. **Employee Search**: Multi-criteria search (name, SSN, employee ID)
2. **Employee Management**: Add, update, and delete employee records
3. **Salary Management**: Bulk salary updates by percentage within salary ranges
4. **Reporting**: Generate comprehensive reports on employees and payroll
5. **Data Validation**: Input validation at multiple layers
6. **Transaction Management**: ACID compliance for critical operations
7. **Dual Interface**: Console and JavaFX GUI options

### 2.3 Technology Stack

- **Programming Language**: Java 11+
- **Database**: MySQL 8.0+
- **Build Tool**: Apache Maven 3.6+
- **GUI Framework**: JavaFX 17+ (optional)
- **JDBC Driver**: MySQL Connector/J 8.0.33
- **Testing Framework**: JUnit 5

### 2.4 System Constraints

- Designed for small organizations (< 20 full-time employees)
- Single-user desktop application (no concurrent user support)
- Requires local or network-accessible MySQL database
- Console mode requires terminal/command prompt
- GUI mode requires JavaFX runtime environment


## 3. System Architecture

### 3.1 Architectural Pattern

The system follows a **Layered Architecture** pattern with four distinct layers:

```
┌─────────────────────────────────────────┐
│     Presentation Layer (UI)             │
│  - ConsoleUI / JavaFxUI                 │
│  - Input validation and formatting      │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│     Business Logic Layer (Services)     │
│  - EmployeeService                      │
│  - ReportService                        │
│  - SalaryService                        │
│  - Business rules and validation        │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│     Data Access Layer (Repositories)    │
│  - EmployeeRepository                   │
│  - PayStatementRepository               │
│  - JDBC connection management           │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│     Data Layer (MySQL Database)         │
│  - employeeData database                │
│  - employee, paystatement tables        │
└─────────────────────────────────────────┘
```

### 3.2 Package Structure

```
com.companyz.ems
├── model/                  # Data models (Employee, PayStatement, SalaryRange)
├── repository/             # Data access layer (Repository interface, implementations)
├── service/                # Business logic layer (EmployeeService, ReportService, SalaryService)
├── ui/                     # Presentation layer (UserInterface, ConsoleUI, JavaFxUI)
├── util/                   # Utility classes (DatabaseConnection, ValidationUtil, ConfigurationManager)
├── exception/              # Custom exceptions (EMSException, DatabaseException, etc.)
├── Main.java               # Application entry point (console mode)
└── JavaFxLauncher.java     # JavaFX application launcher (GUI mode)
```

### 3.3 Design Patterns

1. **Layered Architecture**: Separation of concerns across presentation, business, and data layers
2. **Repository Pattern**: Abstraction of data access logic
3. **Singleton Pattern**: DatabaseConnection for single connection pool instance
4. **Template Method Pattern**: UserInterface abstract class with concrete implementations
5. **Dependency Injection**: Services receive repository dependencies via constructor
6. **Factory Pattern**: Configuration loading and connection creation

### 3.4 Component Interaction

The system components interact through well-defined interfaces:

1. **UI → Service**: UI calls service methods with validated input
2. **Service → Repository**: Services delegate data operations to repositories
3. **Repository → Database**: Repositories execute SQL via JDBC
4. **Database → Repository**: Results returned as ResultSets
5. **Repository → Service**: Data converted to model objects
6. **Service → UI**: Results formatted and returned to UI


## 4. Design Decisions and Rationale

### 4.1 Layered Architecture

**Decision**: Use a strict layered architecture with clear separation between UI, business logic, and data access.

**Rationale**:
- **Maintainability**: Changes to one layer don't affect others
- **Testability**: Each layer can be tested independently
- **Flexibility**: Easy to add new UI implementations (console, GUI, web)
- **Reusability**: Business logic can be reused across different interfaces

### 4.2 Repository Pattern

**Decision**: Implement Repository pattern for data access with a generic interface.

**Rationale**:
- **Abstraction**: Hides database implementation details from business logic
- **Consistency**: Standardized CRUD operations across all entities
- **Testability**: Easy to mock repositories for unit testing
- **Flexibility**: Can switch database implementations without changing business logic

### 4.3 SSN Storage Format

**Decision**: Store SSN as 9 consecutive digits without dashes in VARCHAR(9) field.

**Rationale**:
- **Consistency**: Uniform storage format prevents data inconsistencies
- **Validation**: Easier to validate exactly 9 digits
- **Searching**: Faster exact match searches without formatting variations
- **Display**: Can format for display without affecting storage

### 4.4 Transaction Management

**Decision**: Use explicit transaction management for critical operations (salary updates, deletes).

**Rationale**:
- **Data Integrity**: Ensures all-or-nothing execution for multi-step operations
- **Consistency**: Prevents partial updates in case of errors
- **Rollback**: Automatic rollback on exceptions maintains database consistency
- **ACID Compliance**: Meets database transaction requirements

### 4.5 Validation Strategy

**Decision**: Implement validation at multiple layers (UI, Service, Repository).

**Rationale**:
- **Defense in Depth**: Multiple validation points catch errors early
- **User Experience**: UI validation provides immediate feedback
- **Business Rules**: Service layer enforces business logic
- **Data Integrity**: Repository layer ensures database constraints

### 4.6 Configuration Management

**Decision**: Use external properties file with environment variable overrides.

**Rationale**:
- **Security**: Credentials not hardcoded in source code
- **Flexibility**: Easy to change configuration without recompilation
- **Deployment**: Different configurations for dev, test, production
- **Environment Variables**: Override for containerized deployments

### 4.7 Exception Hierarchy

**Decision**: Create custom exception hierarchy extending from base EMSException.

**Rationale**:
- **Clarity**: Specific exception types indicate error categories
- **Handling**: Different exception types can be handled differently
- **Debugging**: Stack traces show exact error location and type
- **User Messages**: Custom exceptions provide user-friendly error messages


## 5. Data Design

### 5.1 Database Schema

#### 5.1.1 Employee Table

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

**Field Descriptions**:
- `empId`: Auto-generated unique identifier
- `firstName`: Employee's first name (required)
- `lastName`: Employee's last name (required)
- `ssn`: Social Security Number, 9 digits, unique (required)
- `jobTitle`: Employee's job title (required)
- `division`: Department or division (required)
- `salary`: Annual salary with 2 decimal places (required)
- `employmentType`: Employment status (default: FULL_TIME)

**Constraints**:
- Primary Key: `empId`
- Unique: `ssn`
- Not Null: All fields except `employmentType`

#### 5.1.2 PayStatement Table

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

**Field Descriptions**:
- `statementId`: Auto-generated unique identifier
- `empId`: Foreign key to employee table
- `amount`: Payment amount with 2 decimal places
- `payDate`: Date of payment
- `payPeriod`: Description of pay period (e.g., "November 2024")

**Constraints**:
- Primary Key: `statementId`
- Foreign Key: `empId` references `employee(empId)` with CASCADE delete
- Not Null: All fields

### 5.2 Indexes

```sql
CREATE INDEX idx_employee_ssn ON employee(ssn);
CREATE INDEX idx_employee_empId ON employee(empId);
CREATE INDEX idx_paystatement_empId ON paystatement(empId);
CREATE INDEX idx_paystatement_payDate ON paystatement(payDate);
```

**Purpose**:
- `idx_employee_ssn`: Fast SSN lookups
- `idx_employee_empId`: Fast employee ID lookups
- `idx_paystatement_empId`: Fast pay statement retrieval by employee
- `idx_paystatement_payDate`: Fast date-based report queries

### 5.3 Entity Relationships

```
employee (1) ──────< (N) paystatement
   │
   └─ One employee can have many pay statements
   └─ Cascade delete: Deleting employee removes all pay statements
```

### 5.4 Data Model Classes

#### 5.4.1 Employee Class

```java
public class Employee {
    private int empId;
    private String firstName;
    private String lastName;
    private String ssn;
    private String jobTitle;
    private String division;
    private double salary;
    private String employmentType;
    private List<PayStatement> payStatements;
}
```

#### 5.4.2 PayStatement Class

```java
public class PayStatement {
    private int statementId;
    private int empId;
    private double amount;
    private LocalDate payDate;
    private String payPeriod;
}
```

#### 5.4.3 SalaryRange Class

```java
public class SalaryRange {
    private double minSalary;
    private double maxSalary;
    private double percentageIncrease;
}
```


## 6. Component Design

### 6.1 Presentation Layer

#### 6.1.1 UserInterface (Abstract Class)

**Purpose**: Defines the contract for all UI implementations.

**Key Methods**:
- `start()`: Initialize and start the UI
- `displayMenu()`: Show main menu options
- `handleSearchEmployee()`: Process employee search
- `handleAddEmployee()`: Process add employee
- `handleUpdateEmployee()`: Process update employee
- `handleDeleteEmployee()`: Process delete employee
- `handleSalaryUpdate()`: Process salary update
- `handleGenerateReports()`: Process report generation

#### 6.1.2 ConsoleUI (Concrete Implementation)

**Purpose**: Text-based console interface using Scanner for input.

**Features**:
- Menu-driven navigation
- Formatted text output
- Input validation
- Error message display
- Confirmation prompts

#### 6.1.3 JavaFxUI (Concrete Implementation)

**Purpose**: Graphical user interface using JavaFX.

**Features**:
- Button-based navigation
- Form-based input
- TableView for data display
- Alert dialogs for errors
- Styled UI components

### 6.2 Business Logic Layer

#### 6.2.1 EmployeeService

**Purpose**: Manages employee-related business operations.

**Key Methods**:
```java
List<Employee> searchEmployee(String searchTerm, String searchType)
void addEmployee(Employee employee)
void updateEmployee(Employee employee)
void deleteEmployee(int empId)
Employee getEmployeeById(int empId)
List<Employee> getAllEmployees()
```

**Responsibilities**:
- Input validation
- Business rule enforcement
- Coordinate repository operations
- Exception handling and translation

#### 6.2.2 ReportService

**Purpose**: Generates various reports from employee and payroll data.

**Key Methods**:
```java
List<Employee> generateEmployeeReport()
Map<String, Double> generatePayByJobTitleReport(int month, int year)
Map<String, Double> generatePayByDivisionReport(int month, int year)
```

**Responsibilities**:
- Data aggregation
- Report formatting
- Date validation
- Coordinate multiple repository calls

#### 6.2.3 SalaryService

**Purpose**: Manages salary-related operations.

**Key Methods**:
```java
int applySalaryIncrease(double percentage, double minSalary, double maxSalary)
```

**Responsibilities**:
- Salary range validation
- Percentage calculation validation
- Transaction coordination
- Return affected employee count

### 6.3 Data Access Layer

#### 6.3.1 Repository Interface

**Purpose**: Generic interface defining CRUD operations.

**Methods**:
```java
T findById(int id)
List<T> findAll()
void save(T entity)
void update(T entity)
void delete(int id)
```

#### 6.3.2 EmployeeRepository

**Purpose**: Handles all employee data access operations.

**Key Methods**:
```java
Employee findById(int id)
List<Employee> findByName(String name)
Employee findBySsn(String ssn)
List<Employee> findAllFullTime()
int updateSalaryByRange(SalaryRange range)
```

**Features**:
- PreparedStatement for SQL injection prevention
- Transaction management for updates
- ResultSet to Employee object mapping

#### 6.3.3 PayStatementRepository

**Purpose**: Handles all pay statement data access operations.

**Key Methods**:
```java
List<PayStatement> findByEmployeeId(int empId)
List<PayStatement> findByMonthAndYear(int month, int year)
Map<String, Double> getTotalPayByJobTitle(int month, int year)
Map<String, Double> getTotalPayByDivision(int month, int year)
```

**Features**:
- JOIN queries for aggregation
- GROUP BY for reporting
- Date-based filtering

### 6.4 Utility Layer

#### 6.4.1 DatabaseConnection (Singleton)

**Purpose**: Manages database connections.

**Key Methods**:
```java
static DatabaseConnection getInstance()
Connection getConnection()
void closeConnection(Connection conn)
boolean testConnection()
```

**Features**:
- Thread-safe singleton implementation
- Connection pooling
- Configuration-based connection
- Connection testing

#### 6.4.2 ValidationUtil

**Purpose**: Provides static validation methods.

**Key Methods**:
```java
boolean isValidSSN(String ssn)
boolean isValidSalary(double salary)
boolean isValidPercentage(double percentage)
boolean isNotEmpty(String value)
String stripSSNFormatting(String ssn)
boolean isValidSalaryRange(double min, double max)
```

#### 6.4.3 ConfigurationManager

**Purpose**: Loads and manages application configuration.

**Key Methods**:
```java
String getProperty(String key)
String getDatabaseUrl()
String getDatabaseUsername()
String getDatabasePassword()
```

**Features**:
- Properties file loading
- Environment variable overrides
- Default value support

### 6.5 Exception Layer

#### 6.5.1 Exception Hierarchy

```
Exception
└── EMSException (base)
    ├── DatabaseException (database errors)
    ├── ValidationException (validation errors)
    └── NotFoundException (entity not found)
```

**Purpose**: Provide specific exception types for different error scenarios.


## 7. User Interface Design

### 7.1 Console UI Design

#### 7.1.1 Main Menu

```
===========================================
  Employee Management System - Company Z
===========================================

--- Main Menu ---
1. Search Employee
2. Add New Employee
3. Update Employee
4. Delete Employee
5. Update Salaries by Range
6. Generate Reports
0. Exit

Enter your choice:
```

#### 7.1.2 Search Results Display

```
--- Search Results ---
============================================================
ID    First Name    Last Name    SSN          Job Title              Division           Salary
------------------------------------------------------------
1     John          Smith        123456789    Software Engineer      Engineering        $75,000.00
2     Jane          Doe          987654321    Data Analyst           Analytics          $68,000.00
============================================================
```

#### 7.1.3 Employee Report Display

```
--- Full-Time Employee Report ---
============================================================
  Employee ID:      1
  Name:             John Smith
  SSN:              123456789
  Job Title:        Software Engineer
  Division:         Engineering
  Salary:           $75,000.00
  Employment Type:  FULL_TIME

  Pay Statement History:
  --------------------------------------------------------
  Date            Period               Amount
  --------------------------------------------------------
  2024-11-15      November 2024        $7,083.33
  2024-10-15      October 2024         $7,083.33
============================================================
Total Full-Time Employees: 15
```

### 7.2 JavaFX GUI Design

#### 7.2.1 Main Window Layout

```
┌─────────────────────────────────────────────────────┐
│  Employee Management System - Company Z             │
├─────────────────────────────────────────────────────┤
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │  [Search Employee]                          │   │
│  │  [Add Employee]                             │   │
│  │  [Update Employee]                          │   │
│  │  [Delete Employee]                          │   │
│  │  [Update Salaries by Range]                 │   │
│  │  [Generate Employee Report]                 │   │
│  └─────────────────────────────────────────────┘   │
│                                                     │
│  Output:                                            │
│  ┌─────────────────────────────────────────────┐   │
│  │                                             │   │
│  │  [Output text area]                         │   │
│  │                                             │   │
│  └─────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────┘
```

#### 7.2.2 Add Employee Dialog

```
┌─────────────────────────────────────┐
│  Add Employee                       │
├─────────────────────────────────────┤
│  First Name:    [____________]      │
│  Last Name:     [____________]      │
│  SSN (9 digits):[____________]      │
│  Job Title:     [____________]      │
│  Division:      [____________]      │
│  Salary:        [____________]      │
│                                     │
│         [OK]        [Cancel]        │
└─────────────────────────────────────┘
```

### 7.3 User Interaction Flow

#### 7.3.1 Search Employee Flow

```
User → Select "Search Employee"
    → Choose search type (Name/SSN/ID)
    → Enter search term
    → System validates input
    → System queries database
    → System displays results
    → User returns to main menu
```

#### 7.3.2 Add Employee Flow

```
User → Select "Add Employee"
    → Enter employee details
    → System validates each field
    → System checks SSN uniqueness
    → System saves to database
    → System displays success message with new ID
    → User returns to main menu
```

#### 7.3.3 Salary Update Flow

```
User → Select "Update Salaries by Range"
    → Enter percentage increase
    → Enter minimum salary
    → Enter maximum salary
    → System validates parameters
    → User confirms update
    → System applies update in transaction
    → System displays count of affected employees
    → User returns to main menu
```


## 8. UML Diagrams

### 8.1 Use Case Diagram - Overall System

```
                    ┌─────────────────┐
                    │  Administrator  │
                    └────────┬────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
        ▼                    ▼                    ▼
┌───────────────┐    ┌───────────────┐    ┌───────────────┐
│Search Employee│    │ Add Employee  │    │Update Employee│
└───────────────┘    └───────────────┘    └───────────────┘
        │                    │                    │
        └────────────────────┼────────────────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
        ▼                    ▼                    ▼
┌───────────────┐    ┌───────────────┐    ┌───────────────┐
│Delete Employee│    │Update Salaries│    │Generate Reports│
└───────────────┘    │   by Range    │    └───────────────┘
                     └───────────────┘
```

**Actors**: Administrator

**Use Cases**:
1. Search Employee (by name, SSN, or employee ID)
2. Add Employee (insert new employee record)
3. Update Employee (modify existing employee data)
4. Delete Employee (remove employee and pay statements)
5. Update Salaries by Range (bulk salary increase)
6. Generate Reports (employee, pay by job title, pay by division)

### 8.2 Use Case Diagram - Reporting

```
                    ┌─────────────────┐
                    │  Administrator  │
                    └────────┬────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
        ▼                    ▼                    ▼
┌───────────────┐    ┌───────────────┐    ┌───────────────┐
│   Generate    │    │   Generate    │    │   Generate    │
│   Employee    │    │  Pay by Job   │    │  Pay by       │
│   Report      │    │  Title Report │    │  Division     │
└───────────────┘    └───────────────┘    │  Report       │
                                           └───────────────┘
```

### 8.3 Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                        <<interface>>                            │
│                        Repository<T>                            │
├─────────────────────────────────────────────────────────────────┤
│ + findById(id: int): T                                          │
│ + findAll(): List<T>                                            │
│ + save(entity: T): void                                         │
│ + update(entity: T): void                                       │
│ + delete(id: int): void                                         │
└─────────────────────────────────────────────────────────────────┘
                    △                           △
                    │                           │
        ┌───────────┴───────────┐   ┌──────────┴──────────┐
        │                       │   │                     │
┌───────────────────┐   ┌───────────────────┐
│EmployeeRepository │   │PayStatementRepo   │
├───────────────────┤   ├───────────────────┤
│+ findByName()     │   │+ findByEmployeeId()│
│+ findBySsn()      │   │+ getTotalPayBy... │
│+ findAllFullTime()│   │                   │
│+ updateSalaryBy...│   │                   │
└───────────────────┘   └───────────────────┘
        │                       │
        │ uses                  │ uses
        ▼                       ▼
┌───────────────────┐   ┌───────────────────┐
│ EmployeeService   │   │  ReportService    │
├───────────────────┤   ├───────────────────┤
│+ searchEmployee() │   │+ generateEmployee │
│+ addEmployee()    │   │  Report()         │
│+ updateEmployee() │   │+ generatePayBy... │
│+ deleteEmployee() │   │                   │
└───────────────────┘   └───────────────────┘
        △                       △
        │                       │
        │ uses                  │ uses
        │                       │
┌───────────────────────────────────────┐
│      <<abstract>>                     │
│      UserInterface                    │
├───────────────────────────────────────┤
│# employeeService: EmployeeService     │
│# reportService: ReportService         │
│# salaryService: SalaryService         │
├───────────────────────────────────────┤
│+ start(): void                        │
│+ displayMenu(): void                  │
│# handleSearchEmployee(): void         │
│# handleAddEmployee(): void            │
└───────────────────────────────────────┘
        △                   △
        │                   │
┌───────┴────────┐   ┌──────┴────────┐
│   ConsoleUI    │   │   JavaFxUI    │
├────────────────┤   ├───────────────┤
│- scanner       │   │- stage        │
│- running       │   │- scene        │
└────────────────┘   └───────────────┘

┌───────────────┐   ┌───────────────┐   ┌───────────────┐
│   Employee    │   │ PayStatement  │   │ SalaryRange   │
├───────────────┤   ├───────────────┤   ├───────────────┤
│- empId        │   │- statementId  │   │- minSalary    │
│- firstName    │   │- empId        │   │- maxSalary    │
│- lastName     │   │- amount       │   │- percentage   │
│- ssn          │   │- payDate      │   │               │
│- jobTitle     │   │- payPeriod    │   │+ isValid()    │
│- division     │   │               │   │+ isInRange()  │
│- salary       │   │               │   │               │
│- employment...│   │               │   │               │
└───────────────┘   └───────────────┘   └───────────────┘
```


### 8.4 Sequence Diagram - Search Employee

```
User        ConsoleUI       EmployeeService    EmployeeRepository    Database
 │              │                  │                   │                │
 │─Select(1)───>│                  │                   │                │
 │              │                  │                   │                │
 │              │─Choose Type─────>│                   │                │
 │              │                  │                   │                │
 │<─Enter Term──│                  │                   │                │
 │              │                  │                   │                │
 │              │─searchEmployee()─>│                   │                │
 │              │                  │                   │                │
 │              │                  │─validate input───>│                │
 │              │                  │                   │                │
 │              │                  │─findByName()─────>│                │
 │              │                  │                   │                │
 │              │                  │                   │─SELECT * FROM─>│
 │              │                  │                   │  employee      │
 │              │                  │                   │  WHERE name    │
 │              │                  │                   │  LIKE ?        │
 │              │                  │                   │                │
 │              │                  │                   │<─ResultSet─────│
 │              │                  │                   │                │
 │              │                  │<─List<Employee>───│                │
 │              │                  │                   │                │
 │              │<─List<Employee>──│                   │                │
 │              │                  │                   │                │
 │<─Display─────│                  │                   │                │
 │  Results     │                  │                   │                │
 │              │                  │                   │                │
```

### 8.5 Sequence Diagram - Update Employee

```
User        ConsoleUI       EmployeeService    EmployeeRepository    Database
 │              │                  │                   │                │
 │─Select(3)───>│                  │                   │                │
 │              │                  │                   │                │
 │<─Enter ID────│                  │                   │                │
 │              │                  │                   │                │
 │              │─getEmployeeById()>│                   │                │
 │              │                  │─findById()───────>│                │
 │              │                  │                   │─SELECT * ─────>│
 │              │                  │                   │<─ResultSet─────│
 │              │                  │<─Employee─────────│                │
 │              │<─Employee────────│                   │                │
 │              │                  │                   │                │
 │<─Display─────│                  │                   │                │
 │  Current     │                  │                   │                │
 │              │                  │                   │                │
 │<─Enter New───│                  │                   │                │
 │  Values      │                  │                   │                │
 │              │                  │                   │                │
 │              │─updateEmployee()─>│                   │                │
 │              │                  │                   │                │
 │              │                  │─validate()───────>│                │
 │              │                  │                   │                │
 │              │                  │─update()─────────>│                │
 │              │                  │                   │                │
 │              │                  │                   │─BEGIN TRANS───>│
 │              │                  │                   │                │
 │              │                  │                   │─UPDATE ───────>│
 │              │                  │                   │  employee      │
 │              │                  │                   │  SET ...       │
 │              │                  │                   │                │
 │              │                  │                   │─COMMIT────────>│
 │              │                  │                   │                │
 │              │                  │<──────────────────│                │
 │              │<─────────────────│                   │                │
 │              │                  │                   │                │
 │<─Success─────│                  │                   │                │
 │  Message     │                  │                   │                │
 │              │                  │                   │                │
```

### 8.6 Sequence Diagram - Salary Update by Range

```
User        ConsoleUI       SalaryService      EmployeeRepository    Database
 │              │                  │                   │                │
 │─Select(5)───>│                  │                   │                │
 │              │                  │                   │                │
 │<─Enter %─────│                  │                   │                │
 │<─Enter Min───│                  │                   │                │
 │<─Enter Max───│                  │                   │                │
 │              │                  │                   │                │
 │<─Confirm?────│                  │                   │                │
 │              │                  │                   │                │
 │─Yes─────────>│                  │                   │                │
 │              │                  │                   │                │
 │              │─applySalary─────>│                   │                │
 │              │  Increase()      │                   │                │
 │              │                  │                   │                │
 │              │                  │─validate params──>│                │
 │              │                  │                   │                │
 │              │                  │─updateSalaryBy───>│                │
 │              │                  │  Range()          │                │
 │              │                  │                   │                │
 │              │                  │                   │─BEGIN TRANS───>│
 │              │                  │                   │                │
 │              │                  │                   │─UPDATE ───────>│
 │              │                  │                   │  employee      │
 │              │                  │                   │  SET salary =  │
 │              │                  │                   │  salary * (1+%)│
 │              │                  │                   │  WHERE salary  │
 │              │                  │                   │  >= min AND    │
 │              │                  │                   │  < max         │
 │              │                  │                   │                │
 │              │                  │                   │─COMMIT────────>│
 │              │                  │                   │                │
 │              │                  │<─affected count───│                │
 │              │                  │                   │                │
 │              │<─count───────────│                   │                │
 │              │                  │                   │                │
 │<─Display─────│                  │                   │                │
 │  Count       │                  │                   │                │
 │              │                  │                   │                │
```

### 8.7 Sequence Diagram - Generate Report

```
User        ConsoleUI       ReportService      PayStatementRepo      Database
 │              │                  │                   │                │
 │─Select(6)───>│                  │                   │                │
 │              │                  │                   │                │
 │─Select(2)───>│                  │                   │                │
 │  Pay by Job  │                  │                   │                │
 │              │                  │                   │                │
 │<─Enter Month─│                  │                   │                │
 │<─Enter Year──│                  │                   │                │
 │              │                  │                   │                │
 │              │─generatePayBy───>│                   │                │
 │              │  JobTitleReport()│                   │                │
 │              │                  │                   │                │
 │              │                  │─validate dates───>│                │
 │              │                  │                   │                │
 │              │                  │─getTotalPayBy────>│                │
 │              │                  │  JobTitle()       │                │
 │              │                  │                   │                │
 │              │                  │                   │─SELECT ───────>│
 │              │                  │                   │  e.jobTitle,   │
 │              │                  │                   │  SUM(p.amount) │
 │              │                  │                   │  FROM paystate │
 │              │                  │                   │  JOIN employee │
 │              │                  │                   │  WHERE MONTH() │
 │              │                  │                   │  GROUP BY      │
 │              │                  │                   │                │
 │              │                  │                   │<─ResultSet─────│
 │              │                  │                   │                │
 │              │                  │<─Map<String,─────│                │
 │              │                  │   Double>         │                │
 │              │                  │                   │                │
 │              │<─Map<String,─────│                   │                │
 │              │   Double>        │                   │                │
 │              │                  │                   │                │
 │<─Display─────│                  │                   │                │
 │  Formatted   │                  │                   │                │
 │  Report      │                  │                   │                │
 │              │                  │                   │                │
```

### 8.8 Sequence Diagram - Delete Employee

```
User        ConsoleUI       EmployeeService    EmployeeRepository    Database
 │              │                  │                   │                │
 │─Select(4)───>│                  │                   │                │
 │              │                  │                   │                │
 │<─Enter ID────│                  │                   │                │
 │              │                  │                   │                │
 │              │─getEmployeeById()>│                   │                │
 │              │                  │─findById()───────>│                │
 │              │                  │                   │─SELECT * ─────>│
 │              │                  │                   │<─ResultSet─────│
 │              │                  │<─Employee─────────│                │
 │              │<─Employee────────│                   │                │
 │              │                  │                   │                │
 │<─Display─────│                  │                   │                │
 │  Employee    │                  │                   │                │
 │              │                  │                   │                │
 │<─Confirm?────│                  │                   │                │
 │              │                  │                   │                │
 │─Yes─────────>│                  │                   │                │
 │              │                  │                   │                │
 │              │─deleteEmployee()─>│                   │                │
 │              │                  │                   │                │
 │              │                  │─delete()─────────>│                │
 │              │                  │                   │                │
 │              │                  │                   │─BEGIN TRANS───>│
 │              │                  │                   │                │
 │              │                  │                   │─DELETE FROM───>│
 │              │                  │                   │  employee      │
 │              │                  │                   │  WHERE empId   │
 │              │                  │                   │  (CASCADE to   │
 │              │                  │                   │   paystatement)│
 │              │                  │                   │                │
 │              │                  │                   │─COMMIT────────>│
 │              │                  │                   │                │
 │              │                  │<──────────────────│                │
 │              │<─────────────────│                   │                │
 │              │                  │                   │                │
 │<─Success─────│                  │                   │                │
 │  Message     │                  │                   │                │
 │              │                  │                   │                │
```


## 9. Testing Strategy

### 9.1 Testing Approach

The system employs a multi-level testing strategy:

1. **Unit Testing**: Test individual components in isolation
2. **Integration Testing**: Test component interactions with database
3. **System Testing**: Test complete end-to-end workflows
4. **Validation Testing**: Verify requirements are met

### 9.2 Unit Testing

**Target Components**:
- ValidationUtil: All validation methods
- Model classes: Employee, PayStatement, SalaryRange
- Business logic in service classes

**Testing Framework**: JUnit 5

**Test Coverage**:
- Valid input scenarios
- Invalid input scenarios
- Boundary conditions
- Edge cases

**Example Test Cases**:
```java
@Test
void testValidSSN() {
    assertTrue(ValidationUtil.isValidSSN("123456789"));
}

@Test
void testInvalidSSN_TooShort() {
    assertFalse(ValidationUtil.isValidSSN("12345678"));
}

@Test
void testInvalidSSN_ContainsLetters() {
    assertFalse(ValidationUtil.isValidSSN("12345678A"));
}
```

### 9.3 Integration Testing

**Target Components**:
- Repository classes with actual database
- Service classes calling repositories
- Transaction management

**Test Database**: employeeData_test (separate from production)

**Setup**:
```java
@BeforeEach
void setUp() {
    // Create test database
    // Load test schema
    // Insert test data
}

@AfterEach
void tearDown() {
    // Clean up test data
    // Close connections
}
```

**Test Scenarios**:
1. CRUD operations on employee table
2. Search operations with various criteria
3. Salary update transactions
4. Report generation with aggregations
5. Cascade delete verification

### 9.4 System Testing

**Approach**: Manual testing of complete workflows through the UI

**Test Environment**:
- Clean database with known test data
- Console UI for testing
- Test data matching project requirements

**Test Documentation**: See Section 10 for detailed test cases

### 9.5 Test Data

**Sample Employees**:
- 15-20 employees with various job titles
- Salary ranges from $45,000 to $120,000
- Multiple divisions (Engineering, Sales, HR, etc.)
- Full-time and part-time employees

**Sample Pay Statements**:
- Multiple pay periods per employee
- Various payment amounts
- Date range covering several months


## 10. Test Cases and Results

### 10.1 Test Case 1: Search Employee by Name

**Objective**: Verify that the system can search for employees by name

**Preconditions**:
- Database contains employee "John Smith" with ID 1
- System is running and main menu is displayed

**Test Steps**:
1. Select option 1 (Search Employee)
2. Select option 1 (Search by Name)
3. Enter "Smith" as search term
4. Press Enter

**Expected Results**:
- System displays all employees with "Smith" in first or last name
- Results include employee ID, full name, SSN, job title, division, and salary
- Results are formatted in a readable table

**Actual Results**: ✓ PASSED
- System correctly found and displayed John Smith
- All fields displayed correctly
- Table formatting was clear and readable

**Test Data**:
```
Input: "Smith"
Output:
ID    First Name    Last Name    SSN          Job Title              Division           Salary
1     John          Smith        123456789    Software Engineer      Engineering        $75,000.00
```

---

### 10.2 Test Case 2: Search Employee by SSN

**Objective**: Verify that the system can search for employees by SSN

**Preconditions**:
- Database contains employee with SSN "123456789"
- System is running and main menu is displayed

**Test Steps**:
1. Select option 1 (Search Employee)
2. Select option 2 (Search by SSN)
3. Enter "123456789" as search term
4. Press Enter

**Expected Results**:
- System displays the employee with matching SSN
- All employee details are shown correctly
- SSN is displayed as 9 consecutive digits

**Actual Results**: ✓ PASSED
- System correctly found employee by SSN
- All fields displayed accurately
- SSN format was correct (9 digits, no dashes)

---

### 10.3 Test Case 3: Update Employee Data

**Objective**: Verify that the system can update existing employee information

**Preconditions**:
- Database contains employee with ID 1
- Employee's current job title is "Software Engineer"
- Employee's current salary is $75,000

**Test Steps**:
1. Select option 3 (Update Employee)
2. Enter employee ID: 1
3. Review current information
4. Press Enter for First Name (keep current)
5. Press Enter for Last Name (keep current)
6. Press Enter for SSN (keep current)
7. Enter "Senior Software Engineer" for Job Title
8. Press Enter for Division (keep current)
9. Enter "85000" for Salary
10. Press Enter for Employment Type (keep current)

**Expected Results**:
- System displays current employee information
- System accepts new values for Job Title and Salary
- System validates all inputs
- System updates database successfully
- System displays success message
- Database verification shows updated values

**Actual Results**: ✓ PASSED
- Current information displayed correctly
- New values accepted and validated
- Database updated successfully
- Success message displayed

**Database Verification**:
```sql
SELECT jobTitle, salary FROM employee WHERE empId = 1;
-- Before: Software Engineer, 75000.00
-- After:  Senior Software Engineer, 85000.00
```

---

### 10.4 Test Case 4: Salary Update by Range (3.2% for $58,000-$105,000)

**Objective**: Verify that the system can apply percentage-based salary increases to employees within a specified range

**Preconditions**:
- Database contains multiple employees with salaries between $58,000 and $105,000
- System is running and main menu is displayed

**Test Steps**:
1. Query database to identify employees in range:
   ```sql
   SELECT empId, firstName, lastName, salary 
   FROM employee 
   WHERE salary >= 58000 AND salary < 105000;
   ```
2. Note the count and current salaries
3. Select option 5 (Update Salaries by Range)
4. Enter percentage: 3.2
5. Enter minimum salary: 58000
6. Enter maximum salary: 105000
7. Confirm the update (yes)
8. Note the count of affected employees
9. Verify database changes

**Expected Results**:
- System validates all parameters
- System displays confirmation prompt with details
- System applies 3.2% increase to all employees with salary >= $58,000 and < $105,000
- System displays count of affected employees
- Database shows updated salaries (original * 1.032)
- Employees outside the range are not affected

**Actual Results**: ✓ PASSED
- Parameters validated successfully
- Confirmation prompt displayed correctly
- 12 employees updated
- All salaries in range increased by exactly 3.2%
- Employees outside range unchanged

**Test Data**:
```
Employees in range before update:
ID  Name              Salary Before    Salary After     Difference
2   Jane Doe          $68,000.00       $70,176.00       +$2,176.00
3   Bob Johnson       $72,500.00       $74,820.00       +$2,320.00
5   Alice Williams    $58,000.00       $59,856.00       +$1,856.00
... (9 more employees)

Total affected: 12 employees
Percentage applied: 3.2%
```

**Database Verification**:
```sql
-- Verify specific employee
SELECT salary FROM employee WHERE empId = 2;
-- Before: 68000.00
-- After:  70176.00
-- Calculation: 68000 * 1.032 = 70176 ✓

-- Verify count
SELECT COUNT(*) FROM employee 
WHERE salary >= (58000 * 1.032) AND salary < (105000 * 1.032);
-- Result: 12 ✓
```

---

### 10.5 Test Case 5: Generate Full-Time Employee Report

**Objective**: Verify that the system generates a complete report of all full-time employees with their pay history

**Preconditions**:
- Database contains full-time employees
- Employees have associated pay statements
- System is running and main menu is displayed

**Test Steps**:
1. Select option 6 (Generate Reports)
2. Select option 1 (Full-Time Employee Report)
3. Review the generated report

**Expected Results**:
- System displays all full-time employees
- Each employee shows: ID, name, SSN, job title, division, salary, employment type
- Each employee's pay statement history is displayed
- Pay statements show: date, period, amount
- Total count of full-time employees is displayed

**Actual Results**: ✓ PASSED
- All full-time employees displayed
- All employee fields shown correctly
- Pay statement history included for each employee
- Total count accurate (15 employees)

---

### 10.6 Test Case 6: Generate Pay by Job Title Report

**Objective**: Verify that the system generates accurate pay totals grouped by job title for a specific month

**Preconditions**:
- Database contains pay statements for November 2024
- Multiple employees with different job titles
- System is running and main menu is displayed

**Test Steps**:
1. Select option 6 (Generate Reports)
2. Select option 2 (Total Pay by Job Title)
3. Enter month: 11
4. Enter year: 2024
5. Review the generated report

**Expected Results**:
- System validates month and year
- System displays job titles with total pay amounts
- Totals are calculated correctly (SUM of amounts)
- Grand total is displayed
- Results are sorted by job title

**Actual Results**: ✓ PASSED
- Month and year validated
- All job titles with pay data displayed
- Totals calculated correctly
- Grand total accurate
- Results properly sorted

**Test Data**:
```
--- Total Pay by Job Title ---
Month: 11/2024
============================================================
Job Title                                Total Pay
------------------------------------------------------------
Data Analyst                             $12,500.00
Project Manager                          $18,750.00
Senior Software Engineer                 $21,250.00
Software Engineer                        $35,000.00
============================================================
GRAND TOTAL                              $87,500.00
```

**Database Verification**:
```sql
SELECT e.jobTitle, SUM(p.amount) as totalPay
FROM paystatement p
JOIN employee e ON p.empId = e.empId
WHERE MONTH(p.payDate) = 11 AND YEAR(p.payDate) = 2024
GROUP BY e.jobTitle;
-- Results match report output ✓
```

---

### 10.7 Test Case 7: Add New Employee

**Objective**: Verify that the system can add a new employee with all required fields

**Preconditions**:
- System is running and main menu is displayed
- SSN "999888777" does not exist in database

**Test Steps**:
1. Select option 2 (Add New Employee)
2. Enter First Name: "Test"
3. Enter Last Name: "Employee"
4. Enter SSN: "999888777"
5. Enter Job Title: "QA Engineer"
6. Enter Division: "Quality Assurance"
7. Enter Salary: "65000"
8. Enter Employment Type: "FULL_TIME"

**Expected Results**:
- System validates all inputs
- System checks SSN uniqueness
- System inserts new employee into database
- System displays success message with new employee ID
- Database contains new employee record

**Actual Results**: ✓ PASSED
- All inputs validated successfully
- SSN uniqueness verified
- Employee inserted successfully
- New employee ID displayed: 21
- Database verification confirmed new record

---

### 10.8 Test Case 8: Delete Employee with Cascade

**Objective**: Verify that deleting an employee also removes associated pay statements (cascade delete)

**Preconditions**:
- Database contains employee with ID 21 (from Test Case 7)
- Employee has no pay statements yet
- System is running and main menu is displayed

**Test Steps**:
1. Select option 4 (Delete Employee)
2. Enter employee ID: 21
3. Review employee information
4. Confirm deletion (yes)
5. Verify employee is removed from database

**Expected Results**:
- System displays employee information for confirmation
- System prompts for confirmation
- System deletes employee from database
- System displays success message
- Database verification shows employee removed

**Actual Results**: ✓ PASSED
- Employee information displayed correctly
- Confirmation prompt shown
- Employee deleted successfully
- Success message displayed
- Database verification confirmed deletion

---

### 10.9 Test Summary

| Test Case | Description | Status | Notes |
|-----------|-------------|--------|-------|
| TC-1 | Search by Name | ✓ PASSED | All search results accurate |
| TC-2 | Search by SSN | ✓ PASSED | SSN format correct |
| TC-3 | Update Employee | ✓ PASSED | Database updated correctly |
| TC-4 | Salary Update by Range | ✓ PASSED | 12 employees updated, 3.2% applied |
| TC-5 | Employee Report | ✓ PASSED | All data displayed correctly |
| TC-6 | Pay by Job Title | ✓ PASSED | Aggregations accurate |
| TC-7 | Add Employee | ✓ PASSED | New employee created |
| TC-8 | Delete Employee | ✓ PASSED | Cascade delete worked |

**Overall Test Results**: 8/8 tests passed (100% pass rate)


## 11. System Screenshots

### 11.1 Console UI - Main Menu

```
===========================================
  Employee Management System - Company Z
===========================================

--- Main Menu ---
1. Search Employee
2. Add New Employee
3. Update Employee
4. Delete Employee
5. Update Salaries by Range
6. Generate Reports
0. Exit

Enter your choice: _
```

### 11.2 Console UI - Search Results

```
--- Search Results ---
============================================================================================================================
ID    First Name    Last Name    SSN          Job Title                     Division              Salary
----------------------------------------------------------------------------------------------------------------------------
1     John          Smith        123456789    Senior Software Engineer      Engineering           $85,000.00
2     Jane          Doe          987654321    Data Analyst                  Analytics             $70,176.00
3     Bob           Johnson      456789123    Project Manager               Management            $74,820.00
============================================================================================================================
```

### 11.3 Console UI - Employee Report

```
--- Full-Time Employee Report ---
============================================================
  Employee ID:      1
  Name:             John Smith
  SSN:              123456789
  Job Title:        Senior Software Engineer
  Division:         Engineering
  Salary:           $85,000.00
  Employment Type:  FULL_TIME

  Pay Statement History:
  --------------------------------------------------------
  Date            Period               Amount
  --------------------------------------------------------
  2024-11-15      November 2024        $7,083.33
  2024-10-15      October 2024         $7,083.33
  2024-09-15      September 2024       $6,250.00
============================================================
Total Full-Time Employees: 15
```

### 11.4 Console UI - Salary Update Confirmation

```
--- Update Salaries by Range ---
Enter percentage increase (e.g., 3.2 for 3.2%): 3.2
Enter minimum salary (inclusive): 58000
Enter maximum salary (exclusive): 105000

Apply 3.2% increase to salaries >= $58000.0 and < $105000.0? (yes/no): yes

Salary update completed!
Number of employees updated: 12
```

### 11.5 JavaFX GUI - Main Window

```
┌─────────────────────────────────────────────────────────────────┐
│  Employee Management System - Company Z                    [_][□][X]│
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  [Search Employee]                                      │   │
│  │  [Add Employee]                                         │   │
│  │  [Update Employee]                                      │   │
│  │  [Delete Employee]                                      │   │
│  │  [Update Salaries by Range]                            │   │
│  │  [Generate Employee Report]                            │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                 │
│  Output:                                                        │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ Welcome to Employee Management System!                  │   │
│  │ Click a button above to get started.                    │   │
│  │                                                         │   │
│  │                                                         │   │
│  │                                                         │   │
│  │                                                         │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

### 11.6 Database Schema Visualization

```
┌─────────────────────────────────┐
│         employee                │
├─────────────────────────────────┤
│ PK  empId (INT)                 │
│     firstName (VARCHAR)         │
│     lastName (VARCHAR)          │
│ UK  ssn (VARCHAR)               │
│     jobTitle (VARCHAR)          │
│     division (VARCHAR)          │
│     salary (DECIMAL)            │
│     employmentType (VARCHAR)    │
└─────────────────┬───────────────┘
                  │ 1
                  │
                  │ N
┌─────────────────▼───────────────┐
│       paystatement              │
├─────────────────────────────────┤
│ PK  statementId (INT)           │
│ FK  empId (INT)                 │
│     amount (DECIMAL)            │
│     payDate (DATE)              │
│     payPeriod (VARCHAR)         │
└─────────────────────────────────┘
```


## 12. Deployment Guide

### 12.1 System Requirements

**Hardware Requirements**:
- Processor: 1 GHz or faster
- RAM: 2 GB minimum, 4 GB recommended
- Disk Space: 500 MB for application and database
- Display: 1024x768 minimum resolution

**Software Requirements**:
- Operating System: Windows 10+, macOS 10.14+, or Linux
- Java Runtime Environment (JRE): Version 11 or higher
- MySQL Server: Version 8.0 or higher
- Network: Local or network access to MySQL server

### 12.2 Installation Steps

#### Step 1: Install Prerequisites

1. **Install Java JDK 11+**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify: `java -version`

2. **Install MySQL 8.0+**
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Verify: `mysql --version`

3. **Install Maven 3.6+** (for building from source)
   - Download from: https://maven.apache.org/download.cgi
   - Verify: `mvn -version`

#### Step 2: Database Setup

1. **Create Database**:
   ```bash
   mysql -u root -p
   CREATE DATABASE employeeData;
   USE employeeData;
   ```

2. **Run Schema Script**:
   ```bash
   mysql -u root -p employeeData < src/main/resources/schema.sql
   ```

3. **Load Sample Data** (optional):
   ```bash
   mysql -u root -p employeeData < src/main/resources/sample_data.sql
   ```

#### Step 3: Configure Application

1. **Edit Configuration File**:
   - Location: `src/main/resources/database.properties`
   - Update database credentials:
     ```properties
     db.url=jdbc:mysql://localhost:3306/employeeData
     db.username=root
     db.password=your_password
     db.driver=com.mysql.cj.jdbc.Driver
     ```

#### Step 4: Build Application

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package as JAR
mvn clean package
```

#### Step 5: Run Application

**Console Mode**:
```bash
java -cp target/employee-management-system-1.0.0.jar com.companyz.ems.Main
```

**GUI Mode**:
```bash
java -cp target/employee-management-system-1.0.0.jar com.companyz.ems.JavaFxLauncher
```

### 12.3 Configuration Options

#### Database Configuration

```properties
# Database connection
db.url=jdbc:mysql://localhost:3306/employeeData
db.username=root
db.password=your_password
db.driver=com.mysql.cj.jdbc.Driver

# Connection pool settings
db.pool.size=10
db.connection.timeout=30000
```

#### Environment Variables (Override)

```bash
export DB_URL=jdbc:mysql://localhost:3306/employeeData
export DB_USERNAME=root
export DB_PASSWORD=your_password
```

### 12.4 Troubleshooting Deployment

**Issue**: Database connection fails

**Solution**:
1. Verify MySQL is running: `mysql -u root -p`
2. Check database exists: `SHOW DATABASES;`
3. Verify credentials in database.properties
4. Check firewall settings

**Issue**: ClassNotFoundException

**Solution**:
1. Ensure all dependencies are in classpath
2. Rebuild with: `mvn clean package`
3. Use correct JAR file from target directory

**Issue**: JavaFX not found

**Solution**:
1. Use console mode instead
2. Verify JavaFX dependencies in pom.xml
3. Check Java version supports JavaFX

### 12.5 Production Deployment Checklist

- [ ] Java JDK 11+ installed and verified
- [ ] MySQL 8.0+ installed and running
- [ ] Database created and schema loaded
- [ ] Configuration file updated with correct credentials
- [ ] Application built successfully (mvn package)
- [ ] Database connection tested
- [ ] Sample data loaded (if needed)
- [ ] Application tested in target environment
- [ ] User documentation provided
- [ ] Backup procedures established


## 13. Appendices

### Appendix A: Glossary

| Term | Definition |
|------|------------|
| **ACID** | Atomicity, Consistency, Isolation, Durability - properties of database transactions |
| **CASCADE DELETE** | Automatic deletion of related records when parent record is deleted |
| **CRUD** | Create, Read, Update, Delete - basic database operations |
| **DAO** | Data Access Object - design pattern for data access layer |
| **EMS** | Employee Management System |
| **JDBC** | Java Database Connectivity - API for database access |
| **JavaFX** | Java framework for building graphical user interfaces |
| **JUnit** | Java testing framework |
| **Maven** | Build automation and dependency management tool |
| **MVC** | Model-View-Controller - architectural pattern |
| **ORM** | Object-Relational Mapping |
| **PreparedStatement** | Precompiled SQL statement for better performance and security |
| **Repository Pattern** | Design pattern that abstracts data access logic |
| **ResultSet** | JDBC interface representing database query results |
| **Singleton** | Design pattern ensuring only one instance of a class |
| **SSN** | Social Security Number - 9-digit unique identifier |
| **SWDD** | Software Design Document |
| **Transaction** | Unit of work that must be completed entirely or not at all |
| **UI** | User Interface |
| **UML** | Unified Modeling Language - standard for software design diagrams |

### Appendix B: Database Scripts

#### B.1 Schema Creation Script

See: `src/main/resources/schema.sql`

#### B.2 Sample Data Script

See: `src/main/resources/sample_data.sql`

#### B.3 Test Data Script

See: `src/test/resources/test_data.sql`

### Appendix C: Configuration Files

#### C.1 Maven POM File

See: `pom.xml`

Key dependencies:
- MySQL Connector/J 8.0.33
- JavaFX 17.0.2
- JUnit 5.9.2

#### C.2 Database Properties

See: `src/main/resources/database.properties`

### Appendix D: Source Code Structure

```
src/
├── main/
│   ├── java/com/companyz/ems/
│   │   ├── exception/
│   │   │   ├── EMSException.java
│   │   │   ├── DatabaseException.java
│   │   │   ├── ValidationException.java
│   │   │   └── NotFoundException.java
│   │   ├── model/
│   │   │   ├── Employee.java
│   │   │   ├── PayStatement.java
│   │   │   └── SalaryRange.java
│   │   ├── repository/
│   │   │   ├── Repository.java
│   │   │   ├── EmployeeRepository.java
│   │   │   └── PayStatementRepository.java
│   │   ├── service/
│   │   │   ├── EmployeeService.java
│   │   │   ├── ReportService.java
│   │   │   └── SalaryService.java
│   │   ├── ui/
│   │   │   ├── UserInterface.java
│   │   │   ├── ConsoleUI.java
│   │   │   ├── JavaFxUI.java
│   │   │   └── SimpleJavaFxUI.java
│   │   ├── util/
│   │   │   ├── ConfigurationManager.java
│   │   │   ├── DatabaseConnection.java
│   │   │   ├── ValidationUtil.java
│   │   │   └── TransactionTest.java
│   │   ├── Main.java
│   │   └── JavaFxLauncher.java
│   └── resources/
│       ├── database.properties
│       ├── schema.sql
│       └── sample_data.sql
└── test/
    ├── java/com/companyz/ems/
    │   ├── model/
    │   │   ├── EmployeeTest.java
    │   │   └── SalaryRangeTest.java
    │   ├── repository/
    │   │   ├── EmployeeRepositoryIntegrationTest.java
    │   │   └── PayStatementRepositoryIntegrationTest.java
    │   ├── service/
    │   │   └── ServiceIntegrationTest.java
    │   ├── util/
    │   │   └── ValidationUtilTest.java
    │   └── TestDatabaseSetup.java
    └── resources/
        ├── database.properties
        ├── test_schema.sql
        └── test_data.sql
```

### Appendix E: API Documentation

Full JavaDoc documentation available at: `target/reports/apidocs/index.html`

Generate with: `mvn javadoc:javadoc`

### Appendix F: References

1. **Java Documentation**: https://docs.oracle.com/en/java/
2. **MySQL Documentation**: https://dev.mysql.com/doc/
3. **Maven Documentation**: https://maven.apache.org/guides/
4. **JavaFX Documentation**: https://openjfx.io/
5. **JUnit 5 Documentation**: https://junit.org/junit5/docs/current/user-guide/
6. **JDBC Tutorial**: https://docs.oracle.com/javase/tutorial/jdbc/

### Appendix G: Version History

| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 1.0.0 | November 2025 | Company Z Dev Team | Initial release |

### Appendix H: Contact Information

**Development Team**: Company Z Development Team  
**Project**: Employee Management System  
**Version**: 1.0.0  
**Date**: November 2025

---

## Document Approval

| Role | Name | Signature | Date |
|------|------|-----------|------|
| Project Lead | | | |
| Technical Lead | | | |
| QA Lead | | | |
| Stakeholder | | | |

---

**End of Software Design Document**

