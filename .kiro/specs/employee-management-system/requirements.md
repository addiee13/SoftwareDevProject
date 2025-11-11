# Requirements Document

## Introduction

This document specifies the requirements for a minimal employee management system for Company 'Z'. The system provides a basic UX (console or JavaFX GUI) for managing employee data, generating reports, and performing salary updates. The system targets small organizations with fewer than twenty full-time employees and replaces manual database operations with a structured application interface.

## Glossary

- **EMS**: Employee Management System - the software application being developed
- **Employee Record**: A data entity containing employee information including empid, name, SSN, job title, division, and salary
- **Pay Statement**: A record of payment made to an employee for a specific period
- **Administrator**: The user who operates the EMS to manage employee data and generate reports
- **JDBC**: Java Database Connectivity - the API used to connect Java application to MySQL database
- **UX**: User Experience interface - either console-based or JavaFX GUI
- **Beaver**: Database design tool used for schema visualization
- **MySQL Database**: The relational database system storing employee data (employeeData database)

## Requirements

### Requirement 1: Database Schema Enhancement

**User Story:** As an administrator, I want the employee table to include SSN information, so that I can uniquely identify employees using their social security number.

#### Acceptance Criteria

1.1 THE EMS SHALL store employee SSN as a numeric field without dashes in the employee table

1.2 WHEN the administrator views employee information, THE EMS SHALL display the SSN in the format of nine consecutive digits

1.3 THE EMS SHALL validate that SSN entries contain exactly nine numeric digits before storing them in the database

### Requirement 2: Employee Search Functionality

**User Story:** As an administrator, I want to search for employees using their name, SSN, or employee ID, so that I can quickly locate and view specific employee information.

#### Acceptance Criteria

2.1 THE EMS SHALL provide search capability using employee name as a search criterion

2.2 THE EMS SHALL provide search capability using SSN as a search criterion

2.3 THE EMS SHALL provide search capability using employee ID as a search criterion

2.4 WHEN a search matches one or more employees, THE EMS SHALL display all matching employee records with their complete information

2.5 WHEN a search matches no employees, THE EMS SHALL display a message indicating no results were found

### Requirement 3: Employee Data Management

**User Story:** As an administrator, I want to insert, update, and delete employee data through the application interface, so that I can maintain accurate employee records without writing SQL scripts.

#### Acceptance Criteria

3.1 THE EMS SHALL provide functionality to insert new employee records with all required fields

3.2 THE EMS SHALL provide functionality to update existing employee data for any field

3.3 THE EMS SHALL provide functionality to delete employee records from the database

3.4 WHEN an administrator attempts to insert or update employee data, THE EMS SHALL validate all required fields are provided

3.5 WHEN an administrator completes an insert, update, or delete operation, THE EMS SHALL display a confirmation message indicating success or failure

### Requirement 4: Salary Adjustment by Range

**User Story:** As an administrator, I want to apply percentage-based salary increases to employees within a specific salary range, so that I can efficiently process annual raises for targeted compensation bands.

#### Acceptance Criteria

4.1 THE EMS SHALL accept a percentage value for salary increase as input

4.2 THE EMS SHALL accept a minimum salary threshold and maximum salary threshold as input parameters

4.3 WHEN the administrator initiates a salary update, THE EMS SHALL apply the percentage increase only to employees whose current salary is greater than or equal to the minimum threshold and less than the maximum threshold

4.4 WHEN the salary update is complete, THE EMS SHALL display the count of employee records that were updated

4.5 THE EMS SHALL validate that the percentage value is a positive number before processing the update

### Requirement 5: Full-Time Employee Report with Pay History

**User Story:** As an administrator, I want to generate a report showing all full-time employee information along with their pay statement history, so that I can review compensation records comprehensively.

#### Acceptance Criteria

5.1 THE EMS SHALL generate a report containing all full-time employee records

5.2 WHEN generating the employee report, THE EMS SHALL include employee ID, name, SSN, job title, division, and current salary for each employee

5.3 WHEN generating the employee report, THE EMS SHALL include all associated pay statement records for each employee

5.4 THE EMS SHALL display the report in a readable format through the UX interface

### Requirement 6: Monthly Pay Report by Job Title

**User Story:** As an administrator, I want to see total pay amounts grouped by job title for a specific month, so that I can analyze compensation distribution across different roles.

#### Acceptance Criteria

6.1 THE EMS SHALL accept a month and year as input parameters for the report

6.2 THE EMS SHALL calculate the total pay amount for each distinct job title for the specified month

6.3 WHEN generating the report, THE EMS SHALL display each job title with its corresponding total pay amount

6.4 THE EMS SHALL display the report in a readable format through the UX interface

### Requirement 7: Monthly Pay Report by Division

**User Story:** As an administrator, I want to see total pay amounts grouped by division for a specific month, so that I can track departmental compensation costs.

#### Acceptance Criteria

7.1 THE EMS SHALL accept a month and year as input parameters for the report

7.2 THE EMS SHALL calculate the total pay amount for each distinct division for the specified month

7.3 WHEN generating the report, THE EMS SHALL display each division with its corresponding total pay amount

7.4 THE EMS SHALL display the report in a readable format through the UX interface

### Requirement 8: Data Persistence and JDBC Integration

**User Story:** As an administrator, I want all data operations to be persisted to the MySQL database, so that employee information is reliably stored and retrievable.

#### Acceptance Criteria

8.1 THE EMS SHALL establish connection to the MySQL employeeData database using JDBC

8.2 WHEN the administrator performs any data operation, THE EMS SHALL execute the corresponding SQL operation through JDBC

8.3 WHEN a database connection fails, THE EMS SHALL display an error message to the administrator

8.4 THE EMS SHALL close database connections properly after completing operations

### Requirement 9: User Interface

**User Story:** As an administrator, I want a simple interface to access all system features, so that I can perform my tasks without needing to know SQL or database administration.

#### Acceptance Criteria

9.1 THE EMS SHALL provide a user interface implemented as either a console application or JavaFX GUI

9.2 THE EMS SHALL present menu options or controls for all core features including search, insert, update, delete, and report generation

9.3 WHEN the administrator selects a feature, THE EMS SHALL prompt for required inputs in a clear manner

9.4 THE EMS SHALL display operation results and error messages in a user-friendly format

9.5 THE EMS SHALL allow the administrator to return to the main menu after completing any operation
