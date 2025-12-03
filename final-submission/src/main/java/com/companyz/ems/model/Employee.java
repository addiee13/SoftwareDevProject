package com.companyz.ems.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the Employee Management System.
 * Contains employee information including personal details, job information,
 * and associated pay statements.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class Employee {
    private int empId;
    private String firstName;
    private String lastName;
    private String ssn;  // 9 digits, no dashes
    private String jobTitle;
    private String division;
    private double salary;
    private String employmentType;
    private List<PayStatement> payStatements;

    /**
     * Default constructor.
     */
    public Employee() {
        this.payStatements = new ArrayList<>();
        this.employmentType = "FULL_TIME";
    }

    /**
     * Constructor with all fields except empId (for new employees).
     * 
     * @param firstName the employee's first name
     * @param lastName the employee's last name
     * @param ssn the employee's social security number (9 digits)
     * @param jobTitle the employee's job title
     * @param division the employee's division
     * @param salary the employee's annual salary
     * @param employmentType the employment type (e.g., FULL_TIME)
     */
    public Employee(String firstName, String lastName, String ssn, String jobTitle, 
                   String division, double salary, String employmentType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.jobTitle = jobTitle;
        this.division = division;
        this.salary = salary;
        this.employmentType = employmentType;
        this.payStatements = new ArrayList<>();
    }

    /**
     * Constructor with all fields including empId (for existing employees).
     * 
     * @param empId the employee's unique identifier
     * @param firstName the employee's first name
     * @param lastName the employee's last name
     * @param ssn the employee's social security number (9 digits)
     * @param jobTitle the employee's job title
     * @param division the employee's division
     * @param salary the employee's annual salary
     * @param employmentType the employment type (e.g., FULL_TIME)
     */
    public Employee(int empId, String firstName, String lastName, String ssn, 
                   String jobTitle, String division, double salary, String employmentType) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.jobTitle = jobTitle;
        this.division = division;
        this.salary = salary;
        this.employmentType = employmentType;
        this.payStatements = new ArrayList<>();
    }

    // Getters and Setters

    /**
     * Gets the employee ID.
     * 
     * @return the employee ID
     */
    public int getEmpId() {
        return empId;
    }

    /**
     * Sets the employee ID.
     * 
     * @param empId the employee ID to set
     */
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    /**
     * Gets the first name.
     * 
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     * 
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     * 
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     * 
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the full name (first name + last name).
     * 
     * @return the full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Gets the SSN (9 consecutive digits).
     * 
     * @return the SSN
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Sets the SSN.
     * 
     * @param ssn the SSN to set (should be 9 digits)
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * Gets the job title.
     * 
     * @return the job title
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Sets the job title.
     * 
     * @param jobTitle the job title to set
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Gets the division.
     * 
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the division.
     * 
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets the salary.
     * 
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary.
     * 
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the employment type.
     * 
     * @return the employment type
     */
    public String getEmploymentType() {
        return employmentType;
    }

    /**
     * Sets the employment type.
     * 
     * @param employmentType the employment type to set
     */
    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    /**
     * Gets the list of pay statements.
     * 
     * @return the list of pay statements
     */
    public List<PayStatement> getPayStatements() {
        return payStatements;
    }

    /**
     * Sets the list of pay statements.
     * 
     * @param payStatements the list of pay statements to set
     */
    public void setPayStatements(List<PayStatement> payStatements) {
        this.payStatements = payStatements;
    }

    /**
     * Adds a pay statement to the employee's pay history.
     * 
     * @param payStatement the pay statement to add
     */
    public void addPayStatement(PayStatement payStatement) {
        this.payStatements.add(payStatement);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ssn='" + ssn + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", division='" + division + '\'' +
                ", salary=" + salary +
                ", employmentType='" + employmentType + '\'' +
                '}';
    }
}
