package com.companyz.ems.model;

import java.time.LocalDate;

/**
 * Represents a pay statement for an employee.
 * Contains payment information including amount, date, and period.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class PayStatement {
    private int statementId;
    private int empId;
    private double amount;
    private LocalDate payDate;
    private String payPeriod;

    /**
     * Default constructor.
     */
    public PayStatement() {
    }

    /**
     * Constructor without statementId (for new pay statements).
     * 
     * @param empId the employee ID
     * @param amount the payment amount
     * @param payDate the payment date
     * @param payPeriod the pay period (e.g., "January 2024")
     */
    public PayStatement(int empId, double amount, LocalDate payDate, String payPeriod) {
        this.empId = empId;
        this.amount = amount;
        this.payDate = payDate;
        this.payPeriod = payPeriod;
    }

    /**
     * Constructor with all fields (for existing pay statements).
     * 
     * @param statementId the statement ID
     * @param empId the employee ID
     * @param amount the payment amount
     * @param payDate the payment date
     * @param payPeriod the pay period (e.g., "January 2024")
     */
    public PayStatement(int statementId, int empId, double amount, LocalDate payDate, String payPeriod) {
        this.statementId = statementId;
        this.empId = empId;
        this.amount = amount;
        this.payDate = payDate;
        this.payPeriod = payPeriod;
    }

    // Getters and Setters

    /**
     * Gets the statement ID.
     * 
     * @return the statement ID
     */
    public int getStatementId() {
        return statementId;
    }

    /**
     * Sets the statement ID.
     * 
     * @param statementId the statement ID to set
     */
    public void setStatementId(int statementId) {
        this.statementId = statementId;
    }

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
     * Gets the payment amount.
     * 
     * @return the payment amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the payment amount.
     * 
     * @param amount the payment amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the payment date.
     * 
     * @return the payment date
     */
    public LocalDate getPayDate() {
        return payDate;
    }

    /**
     * Sets the payment date.
     * 
     * @param payDate the payment date to set
     */
    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    /**
     * Gets the pay period.
     * 
     * @return the pay period
     */
    public String getPayPeriod() {
        return payPeriod;
    }

    /**
     * Sets the pay period.
     * 
     * @param payPeriod the pay period to set
     */
    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    @Override
    public String toString() {
        return "PayStatement{" +
                "statementId=" + statementId +
                ", empId=" + empId +
                ", amount=" + amount +
                ", payDate=" + payDate +
                ", payPeriod='" + payPeriod + '\'' +
                '}';
    }
}
