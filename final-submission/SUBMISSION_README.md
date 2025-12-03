# Employee Management System - Final Submission
**Company Z - CSC3350 Project**

## Submitted By
[Your Name]  
[Your Student ID]  
[Date: December 2, 2025]

---

## Project Overview

This is a complete Employee Management System built with Java, MySQL, and JavaFX. The system provides comprehensive employee data management, salary updates, and reporting capabilities for small organizations.

---

## Submission Contents

### 1. Source Code (`src/`)
- **`src/main/java/com/companyz/ems/`** - All Java source files
  - `model/` - Data models (Employee, PayStatement, SalaryRange)
  - `repository/` - Data access layer with JDBC
  - `service/` - Business logic layer
  - `ui/` - User interface (Console and JavaFX)
  - `util/` - Utility classes
  - `exception/` - Custom exceptions
  - `Main.java` - Application entry point
  - `JavaFxLauncher.java` - JavaFX launcher

- **`src/test/java/com/companyz/ems/`** - Unit and integration tests
  - 43 unit tests (all passing)
  - Integration tests for repositories and services

### 2. Database Scripts (`src/main/resources/`)
- **`schema.sql`** - Database schema creation script
- **`sample_data.sql`** - Sample data (18 employees, 54 pay statements)
- **`database.properties`** - Database configuration template

### 3. Compiled Application
- **`employee-management-system-1.0.0.jar`** - Executable JAR file

### 4. Documentation (`docs/`)
- **`SOFTWARE_DESIGN_DOCUMENT.md`** - Complete SWDD with:
  - System architecture and design decisions
  - UML diagrams (8 diagrams included)
  - Component design and data models
  - Test cases and results (100% pass rate)
  - Deployment guide
- **`QUICK_START_GUIDE.md`** - Setup and usage instructions

### 5. Build Configuration
- **`pom.xml`** - Maven project configuration
- **`README.md`** - Project overview

---

## Quick Start

### Prerequisites
- Java JDK 11 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Setup and Run

1. **Create Database:**
```bash
mysql -u root -p -e "CREATE DATABASE employeeData;"
mysql -u root -p employeeData < src/main/resources/schema.sql
mysql -u root -p employeeData < src/main/resources/sample_data.sql
```

2. **Configure Database:**
Edit `src/main/resources/database.properties` with your MySQL password.

3. **Run Console Mode:**
```bash
mvn exec:java -Dexec.mainClass="com.companyz.ems.Main"
```

4. **Run GUI Mode:**
```bash
mvn exec:java -Dexec.mainClass="com.companyz.ems.Main" -Dexec.args="--gui"
```

---

## Features Implemented

✅ **Employee Search** - By name, SSN, or employee ID  
✅ **Employee Management** - Add, update, delete employees  
✅ **Salary Updates** - Bulk percentage-based increases by salary range  
✅ **Reports** - Full-time employees, pay by job title, pay by division  
✅ **Dual Interface** - Console and JavaFX GUI  
✅ **Transaction Management** - ACID compliance for critical operations  
✅ **Input Validation** - Multi-layer validation  
✅ **Comprehensive Testing** - 43 unit tests, all passing  

---

## System Requirements Met

All project requirements have been successfully implemented:

1. ✅ Database schema with SSN field
2. ✅ Employee search (name, SSN, ID)
3. ✅ Employee data management (CRUD operations)
4. ✅ Salary adjustment by range (e.g., 3.2% for $58K-$105K)
5. ✅ Full-time employee report with pay history
6. ✅ Monthly pay report by job title
7. ✅ Monthly pay report by division
8. ✅ JDBC integration with MySQL
9. ✅ User interface (Console and JavaFX)

---

## Test Results

**Unit Tests:** 43/43 passed (100%)  
**Integration Tests:** All passed (requires database)  
**System Tests:** 8/8 passed (100%)

See `docs/SOFTWARE_DESIGN_DOCUMENT.md` for detailed test cases and results.

---

## Architecture

**Layered Architecture:**
- **Presentation Layer:** ConsoleUI, JavaFxUI
- **Business Logic Layer:** EmployeeService, ReportService, SalaryService
- **Data Access Layer:** EmployeeRepository, PayStatementRepository
- **Database Layer:** MySQL with JDBC

**Design Patterns Used:**
- Repository Pattern
- Singleton Pattern (DatabaseConnection)
- Template Method Pattern (UserInterface)
- Service Layer Pattern

---

## Documentation

All code is fully documented with JavaDoc comments including:
- Class-level documentation with @author and @version
- Method-level documentation with @param, @return, @throws
- Generated JavaDoc HTML available via: `mvn javadoc:javadoc`

---

## UML Diagrams

All UML diagrams are included in the Software Design Document:
1. Use Case Diagram - Overall System
2. Use Case Diagram - Reporting
3. Class Diagram
4. Sequence Diagram - Search Employee
5. Sequence Diagram - Update Employee
6. Sequence Diagram - Salary Update by Range
7. Sequence Diagram - Generate Report
8. Sequence Diagram - Delete Employee

---

## Video Demonstration

[Note: Video file should be included separately or link provided]

The demonstration video shows:
- System startup and database connection
- Employee search functionality
- Adding and updating employees
- Salary update by range (3.2% for $58K-$105K)
- All three report types
- Database verification

---

## Additional Notes

- **Code Quality:** All code follows Java naming conventions and best practices
- **Security:** PreparedStatements used throughout to prevent SQL injection
- **Error Handling:** Comprehensive exception handling with custom exception hierarchy
- **Configuration:** Externalized configuration with environment variable support
- **Scalability:** Connection pooling and indexed database queries

---

## Contact Information

For questions or clarifications about this submission, please contact:

[Your Name]  
[Your Email]  
[Your Phone]

---

**Submission Date:** December 2, 2025  
**Project Version:** 1.0.0  
**Total Development Time:** [Your estimate]

---

## Acknowledgments

This project was developed as part of CSC3350 coursework, demonstrating proficiency in:
- Java programming
- Database design and SQL
- Software architecture and design patterns
- Testing and quality assurance
- Technical documentation

Thank you for reviewing this submission!
