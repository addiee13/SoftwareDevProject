-- Employee Management System Database Schema
-- Company Z

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS employeeData;
USE employeeData;

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS paystatement;
DROP TABLE IF EXISTS employee;

-- Create employee table
CREATE TABLE employee (
    empId INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    ssn VARCHAR(9) NOT NULL UNIQUE,
    jobTitle VARCHAR(100) NOT NULL,
    division VARCHAR(100) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    employmentType VARCHAR(20) DEFAULT 'FULL_TIME',
    CONSTRAINT chk_salary CHECK (salary >= 0)
);

-- Create paystatement table
CREATE TABLE paystatement (
    statementId INT PRIMARY KEY AUTO_INCREMENT,
    empId INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payDate DATE NOT NULL,
    payPeriod VARCHAR(20) NOT NULL,
    FOREIGN KEY (empId) REFERENCES employee(empId) ON DELETE CASCADE,
    CONSTRAINT chk_amount CHECK (amount >= 0)
);

-- Create indexes for performance
CREATE INDEX idx_employee_ssn ON employee(ssn);
CREATE INDEX idx_employee_empId ON employee(empId);
CREATE INDEX idx_paystatement_empId ON paystatement(empId);
CREATE INDEX idx_paystatement_payDate ON paystatement(payDate);

-- Display table structures
DESCRIBE employee;
DESCRIBE paystatement;
