# Employee Management System - Quick Start Guide

## Table of Contents
1. [System Requirements](#system-requirements)
2. [Database Setup](#database-setup)
3. [Configuration](#configuration)
4. [Building the Application](#building-the-application)
5. [Running the Application](#running-the-application)
6. [Using the Features](#using-the-features)
7. [Troubleshooting](#troubleshooting)

## System Requirements

### Required Software
- **Java Development Kit (JDK)**: Version 11 or higher
  - Download from: https://www.oracle.com/java/technologies/downloads/
  - Verify installation: `java -version`
  
- **MySQL Database**: Version 8.0 or higher
  - Download from: https://dev.mysql.com/downloads/mysql/
  - Verify installation: `mysql --version`
  
- **Apache Maven**: Version 3.6 or higher
  - Download from: https://maven.apache.org/download.cgi
  - Verify installation: `mvn -version`

### Optional Software
- **JavaFX**: Version 17 or higher (for GUI mode)
  - Included as Maven dependency, no separate installation needed

## Database Setup

### Step 1: Create Database

1. Start MySQL server
2. Connect to MySQL as root user:
   ```bash
   mysql -u root -p
   ```

3. Create the database:
   ```sql
   CREATE DATABASE employeeData;
   USE employeeData;
   ```

### Step 2: Create Schema

Run the schema creation script:
```bash
mysql -u root -p employeeData < src/main/resources/schema.sql
```

Or manually execute the SQL:
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

CREATE TABLE paystatement (
    statementId INT PRIMARY KEY AUTO_INCREMENT,
    empId INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payDate DATE NOT NULL,
    payPeriod VARCHAR(20) NOT NULL,
    FOREIGN KEY (empId) REFERENCES employee(empId) ON DELETE CASCADE
);

CREATE INDEX idx_employee_ssn ON employee(ssn);
CREATE INDEX idx_employee_empId ON employee(empId);
```

### Step 3: Load Sample Data

Load sample employee and pay statement data:
```bash
mysql -u root -p employeeData < src/main/resources/sample_data.sql
```

This will insert 15-20 sample employees with various job titles and divisions, along with pay statement records.

## Configuration

### Database Connection Configuration

1. Navigate to `src/main/resources/database.properties`

2. Update the configuration with your database credentials:
   ```properties
   db.url=jdbc:mysql://localhost:3306/employeeData
   db.username=root
   db.password=your_password_here
   db.driver=com.mysql.cj.jdbc.Driver
   db.pool.size=10
   db.connection.timeout=30000
   ```

3. **Important**: Replace `your_password_here` with your actual MySQL root password

### Environment Variable Overrides (Optional)

You can override configuration using environment variables:
```bash
export DB_URL=jdbc:mysql://localhost:3306/employeeData
export DB_USERNAME=root
export DB_PASSWORD=your_password
```

## Building the Application

### Compile the Project

```bash
mvn clean compile
```

### Run Tests

```bash
mvn test
```

### Package as JAR

```bash
mvn clean package
```

This creates an executable JAR file in the `target/` directory.

## Running the Application

### Option 1: Console Mode (Recommended)

Run the console-based interface:
```bash
mvn exec:java -Dexec.mainClass="com.companyz.ems.Main"
```

Or use the provided script:
```bash
./run-console.sh
```

Or run the JAR directly:
```bash
java -cp target/employee-management-system-1.0.0.jar com.companyz.ems.Main
```

### Option 2: JavaFX GUI Mode

Run the graphical user interface:
```bash
mvn exec:java -Dexec.mainClass="com.companyz.ems.JavaFxLauncher"
```

Or use the provided script:
```bash
./run-javafx.sh
```

**Note**: JavaFX mode requires JavaFX libraries to be properly configured.

## Using the Features

### Main Menu

When you start the application in console mode, you'll see:
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

### Feature 1: Search Employee

Search for employees by name, SSN, or employee ID.

**Steps:**
1. Select option `1` from the main menu
2. Choose search type:
   - `1` for Name search
   - `2` for SSN search (9 digits)
   - `3` for Employee ID search
3. Enter the search term
4. View the results

**Example:**
```
Search by:
1. Name
2. SSN
3. Employee ID
Enter choice: 1
Enter name: Smith

--- Search Results ---
ID    First Name    Last Name    SSN          Job Title              Division           Salary
1     John          Smith        123456789    Software Engineer      Engineering        $75,000.00
```

### Feature 2: Add New Employee

Add a new employee to the system.

**Steps:**
1. Select option `2` from the main menu
2. Enter employee information when prompted:
   - First Name
   - Last Name
   - SSN (9 digits, no dashes)
   - Job Title
   - Division
   - Salary (numeric value)
   - Employment Type (default: FULL_TIME)
3. System validates and saves the employee
4. New employee ID is displayed

**Example:**
```
--- Add New Employee ---
First Name: Jane
Last Name: Doe
SSN (9 digits): 987654321
Job Title: Data Analyst
Division: Analytics
Salary: 68000
Employment Type (default: FULL_TIME): FULL_TIME

Employee added successfully! Employee ID: 21
```

### Feature 3: Update Employee

Update existing employee information.

**Steps:**
1. Select option `3` from the main menu
2. Enter the Employee ID to update
3. Current information is displayed
4. Enter new values (press Enter to keep current value)
5. System validates and updates the employee

**Example:**
```
--- Update Employee ---
Enter Employee ID to update: 1

Current Employee Information:
  Employee ID:      1
  Name:             John Smith
  SSN:              123456789
  Job Title:        Software Engineer
  Division:         Engineering
  Salary:           $75,000.00
  Employment Type:  FULL_TIME

Enter new values (press Enter to keep current value):
First Name [John]: 
Last Name [Smith]: 
SSN [123456789]: 
Job Title [Software Engineer]: Senior Software Engineer
Division [Engineering]: 
Salary [75000.00]: 85000
Employment Type [FULL_TIME]: 

Employee updated successfully!
```

### Feature 4: Delete Employee

Delete an employee from the system.

**Steps:**
1. Select option `4` from the main menu
2. Enter the Employee ID to delete
3. Review employee information
4. Confirm deletion (yes/no)
5. Employee and associated pay statements are deleted

**Example:**
```
--- Delete Employee ---
Enter Employee ID to delete: 21

Employee to delete:
  Employee ID:      21
  Name:             Jane Doe
  SSN:              987654321
  Job Title:        Data Analyst
  Division:         Analytics
  Salary:           $68,000.00
  Employment Type:  FULL_TIME

Are you sure you want to delete this employee? (yes/no): yes

Employee deleted successfully!
```

### Feature 5: Update Salaries by Range

Apply percentage-based salary increases to employees within a salary range.

**Steps:**
1. Select option `5` from the main menu
2. Enter percentage increase (e.g., 3.2 for 3.2%)
3. Enter minimum salary (inclusive)
4. Enter maximum salary (exclusive)
5. Confirm the update
6. System displays count of employees updated

**Example:**
```
--- Update Salaries by Range ---
Enter percentage increase (e.g., 3.2 for 3.2%): 3.2
Enter minimum salary (inclusive): 58000
Enter maximum salary (exclusive): 105000

Apply 3.2% increase to salaries >= $58000.0 and < $105000.0? (yes/no): yes

Salary update completed!
Number of employees updated: 12
```

### Feature 6: Generate Reports

Generate various reports about employees and payroll.

**Steps:**
1. Select option `6` from the main menu
2. Choose report type:
   - `1` Full-Time Employee Report with Pay History
   - `2` Total Pay by Job Title (Monthly)
   - `3` Total Pay by Division (Monthly)
3. For monthly reports, enter month (1-12) and year
4. View the formatted report

**Example - Employee Report:**
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

**Example - Pay by Job Title Report:**
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

## Troubleshooting

### Database Connection Issues

**Problem**: "Failed to connect to database"

**Solutions**:
1. Verify MySQL is running:
   ```bash
   mysql -u root -p
   ```

2. Check database.properties configuration:
   - Correct hostname (usually `localhost`)
   - Correct port (usually `3306`)
   - Correct database name (`employeeData`)
   - Correct username and password

3. Test connection manually:
   ```bash
   mysql -u root -p -h localhost -P 3306 employeeData
   ```

4. Check firewall settings if using remote database

### Compilation Errors

**Problem**: "Package does not exist" or "Cannot find symbol"

**Solutions**:
1. Clean and rebuild:
   ```bash
   mvn clean compile
   ```

2. Update Maven dependencies:
   ```bash
   mvn dependency:resolve
   ```

3. Verify Java version:
   ```bash
   java -version
   ```
   Should be Java 11 or higher

### Runtime Errors

**Problem**: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"

**Solution**: Ensure MySQL Connector dependency is in pom.xml and run:
```bash
mvn clean package
```

**Problem**: "ValidationException: Invalid SSN"

**Solution**: SSN must be exactly 9 digits with no dashes or spaces
- Correct: `123456789`
- Incorrect: `123-45-6789` or `12345678`

**Problem**: "DatabaseException: Duplicate entry for key 'ssn'"

**Solution**: SSN must be unique. Use a different SSN or update the existing employee.

### JavaFX Issues (GUI Mode)

**Problem**: "Error initializing QuantumRenderer"

**Solutions**:
1. Use console mode instead:
   ```bash
   ./run-console.sh
   ```

2. Verify JavaFX is properly installed:
   ```bash
   mvn dependency:tree | grep javafx
   ```

3. On macOS, use the JavaFxLauncher:
   ```bash
   mvn exec:java -Dexec.mainClass="com.companyz.ems.JavaFxLauncher"
   ```

### Performance Issues

**Problem**: Slow query performance

**Solutions**:
1. Verify indexes are created:
   ```sql
   SHOW INDEX FROM employee;
   SHOW INDEX FROM paystatement;
   ```

2. Analyze query performance:
   ```sql
   EXPLAIN SELECT * FROM employee WHERE ssn = '123456789';
   ```

3. Increase connection pool size in database.properties:
   ```properties
   db.pool.size=20
   ```

## Additional Resources

- **JavaDoc Documentation**: `target/reports/apidocs/index.html`
- **System Test Cases**: `docs/SYSTEM_TEST_CASES.md`
- **User Guide**: `docs/USER_GUIDE.md`
- **Source Code**: `src/main/java/com/companyz/ems/`
- **Database Scripts**: `src/main/resources/`

## Support

For issues or questions:
1. Check the troubleshooting section above
2. Review the JavaDoc documentation
3. Examine the system test cases for examples
4. Contact the development team

---

**Version**: 1.0.0  
**Last Updated**: November 2025  
**Author**: Company Z Development Team
