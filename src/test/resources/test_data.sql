-- Test Data for Integration Tests
USE employeeData_test;

-- Clear existing data
DELETE FROM paystatement;
DELETE FROM employee;

-- Reset auto-increment
ALTER TABLE employee AUTO_INCREMENT = 1;
ALTER TABLE paystatement AUTO_INCREMENT = 1;

-- Insert test employees
INSERT INTO employee (firstName, lastName, ssn, jobTitle, division, salary, employmentType) VALUES
('Test', 'Employee1', '111111111', 'Software Engineer', 'Engineering', 75000.00, 'FULL_TIME'),
('Test', 'Employee2', '222222222', 'Senior Developer', 'Engineering', 95000.00, 'FULL_TIME'),
('Test', 'Employee3', '333333333', 'Manager', 'Management', 110000.00, 'FULL_TIME'),
('Test', 'Employee4', '444444444', 'Analyst', 'Finance', 65000.00, 'FULL_TIME'),
('Test', 'Employee5', '555555555', 'Engineer', 'Engineering', 55000.00, 'FULL_TIME');

-- Insert test pay statements
INSERT INTO paystatement (empId, amount, payDate, payPeriod) VALUES
(1, 6250.00, '2024-01-31', 'January 2024'),
(1, 6250.00, '2024-02-29', 'February 2024'),
(2, 7916.67, '2024-01-31', 'January 2024'),
(2, 7916.67, '2024-02-29', 'February 2024'),
(3, 9166.67, '2024-01-31', 'January 2024'),
(4, 5416.67, '2024-01-31', 'January 2024'),
(5, 4583.33, '2024-01-31', 'January 2024');
