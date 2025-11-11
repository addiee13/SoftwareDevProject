-- Sample Data for Employee Management System
-- Company Z - Less than 20 full-time employees

USE employeeData;

-- Insert sample employees (18 employees with various job titles and divisions)
INSERT INTO employee (firstName, lastName, ssn, jobTitle, division, salary, employmentType) VALUES
('John', 'Smith', '123456789', 'Software Engineer', 'Engineering', 75000.00, 'FULL_TIME'),
('Sarah', 'Johnson', '234567890', 'Senior Developer', 'Engineering', 95000.00, 'FULL_TIME'),
('Michael', 'Williams', '345678901', 'Project Manager', 'Management', 110000.00, 'FULL_TIME'),
('Emily', 'Brown', '456789012', 'HR Manager', 'Human Resources', 85000.00, 'FULL_TIME'),
('David', 'Jones', '567890123', 'Marketing Director', 'Marketing', 105000.00, 'FULL_TIME'),
('Jessica', 'Garcia', '678901234', 'Accountant', 'Finance', 65000.00, 'FULL_TIME'),
('Daniel', 'Martinez', '789012345', 'Sales Representative', 'Sales', 55000.00, 'FULL_TIME'),
('Ashley', 'Rodriguez', '890123456', 'Software Engineer', 'Engineering', 78000.00, 'FULL_TIME'),
('Matthew', 'Wilson', '901234567', 'QA Engineer', 'Engineering', 70000.00, 'FULL_TIME'),
('Amanda', 'Anderson', '012345678', 'Marketing Specialist', 'Marketing', 60000.00, 'FULL_TIME'),
('Christopher', 'Taylor', '112233445', 'Senior Accountant', 'Finance', 80000.00, 'FULL_TIME'),
('Jennifer', 'Thomas', '223344556', 'HR Specialist', 'Human Resources', 58000.00, 'FULL_TIME'),
('Joshua', 'Moore', '334455667', 'Sales Manager', 'Sales', 92000.00, 'FULL_TIME'),
('Michelle', 'Jackson', '445566778', 'Software Architect', 'Engineering', 120000.00, 'FULL_TIME'),
('Andrew', 'Martin', '556677889', 'Business Analyst', 'Management', 72000.00, 'FULL_TIME'),
('Stephanie', 'Lee', '667788990', 'Content Writer', 'Marketing', 52000.00, 'FULL_TIME'),
('Ryan', 'Perez', '778899001', 'DevOps Engineer', 'Engineering', 88000.00, 'FULL_TIME'),
('Nicole', 'White', '889900112', 'Financial Analyst', 'Finance', 68000.00, 'FULL_TIME');

-- Insert sample pay statements for various months
-- January 2024 pay statements
INSERT INTO paystatement (empId, amount, payDate, payPeriod) VALUES
(1, 6250.00, '2024-01-31', 'January 2024'),
(2, 7916.67, '2024-01-31', 'January 2024'),
(3, 9166.67, '2024-01-31', 'January 2024'),
(4, 7083.33, '2024-01-31', 'January 2024'),
(5, 8750.00, '2024-01-31', 'January 2024'),
(6, 5416.67, '2024-01-31', 'January 2024'),
(7, 4583.33, '2024-01-31', 'January 2024'),
(8, 6500.00, '2024-01-31', 'January 2024'),
(9, 5833.33, '2024-01-31', 'January 2024'),
(10, 5000.00, '2024-01-31', 'January 2024'),
(11, 6666.67, '2024-01-31', 'January 2024'),
(12, 4833.33, '2024-01-31', 'January 2024'),
(13, 7666.67, '2024-01-31', 'January 2024'),
(14, 10000.00, '2024-01-31', 'January 2024'),
(15, 6000.00, '2024-01-31', 'January 2024'),
(16, 4333.33, '2024-01-31', 'January 2024'),
(17, 7333.33, '2024-01-31', 'January 2024'),
(18, 5666.67, '2024-01-31', 'January 2024');

-- February 2024 pay statements
INSERT INTO paystatement (empId, amount, payDate, payPeriod) VALUES
(1, 6250.00, '2024-02-29', 'February 2024'),
(2, 7916.67, '2024-02-29', 'February 2024'),
(3, 9166.67, '2024-02-29', 'February 2024'),
(4, 7083.33, '2024-02-29', 'February 2024'),
(5, 8750.00, '2024-02-29', 'February 2024'),
(6, 5416.67, '2024-02-29', 'February 2024'),
(7, 4583.33, '2024-02-29', 'February 2024'),
(8, 6500.00, '2024-02-29', 'February 2024'),
(9, 5833.33, '2024-02-29', 'February 2024'),
(10, 5000.00, '2024-02-29', 'February 2024'),
(11, 6666.67, '2024-02-29', 'February 2024'),
(12, 4833.33, '2024-02-29', 'February 2024'),
(13, 7666.67, '2024-02-29', 'February 2024'),
(14, 10000.00, '2024-02-29', 'February 2024'),
(15, 6000.00, '2024-02-29', 'February 2024'),
(16, 4333.33, '2024-02-29', 'February 2024'),
(17, 7333.33, '2024-02-29', 'February 2024'),
(18, 5666.67, '2024-02-29', 'February 2024');

-- March 2024 pay statements
INSERT INTO paystatement (empId, amount, payDate, payPeriod) VALUES
(1, 6250.00, '2024-03-31', 'March 2024'),
(2, 7916.67, '2024-03-31', 'March 2024'),
(3, 9166.67, '2024-03-31', 'March 2024'),
(4, 7083.33, '2024-03-31', 'March 2024'),
(5, 8750.00, '2024-03-31', 'March 2024'),
(6, 5416.67, '2024-03-31', 'March 2024'),
(7, 4583.33, '2024-03-31', 'March 2024'),
(8, 6500.00, '2024-03-31', 'March 2024'),
(9, 5833.33, '2024-03-31', 'March 2024'),
(10, 5000.00, '2024-03-31', 'March 2024'),
(11, 6666.67, '2024-03-31', 'March 2024'),
(12, 4833.33, '2024-03-31', 'March 2024'),
(13, 7666.67, '2024-03-31', 'March 2024'),
(14, 10000.00, '2024-03-31', 'March 2024'),
(15, 6000.00, '2024-03-31', 'March 2024'),
(16, 4333.33, '2024-03-31', 'March 2024'),
(17, 7333.33, '2024-03-31', 'March 2024'),
(18, 5666.67, '2024-03-31', 'March 2024');

-- Verify data insertion
SELECT COUNT(*) AS 'Total Employees' FROM employee;
SELECT COUNT(*) AS 'Total Pay Statements' FROM paystatement;

-- Show sample data
SELECT * FROM employee LIMIT 5;
SELECT * FROM paystatement LIMIT 5;
