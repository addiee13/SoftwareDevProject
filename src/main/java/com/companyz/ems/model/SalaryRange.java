package com.companyz.ems.model;

/**
 * Represents a salary range for bulk salary updates.
 * Contains minimum and maximum salary thresholds and the percentage increase to apply.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class SalaryRange {
    private double minSalary;
    private double maxSalary;
    private double percentageIncrease;

    /**
     * Default constructor.
     */
    public SalaryRange() {
    }

    /**
     * Constructor with all fields.
     * 
     * @param minSalary the minimum salary threshold (inclusive)
     * @param maxSalary the maximum salary threshold (exclusive)
     * @param percentageIncrease the percentage increase to apply
     */
    public SalaryRange(double minSalary, double maxSalary, double percentageIncrease) {
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.percentageIncrease = percentageIncrease;
    }

    // Getters and Setters

    /**
     * Gets the minimum salary threshold.
     * 
     * @return the minimum salary
     */
    public double getMinSalary() {
        return minSalary;
    }

    /**
     * Sets the minimum salary threshold.
     * 
     * @param minSalary the minimum salary to set
     */
    public void setMinSalary(double minSalary) {
        this.minSalary = minSalary;
    }

    /**
     * Gets the maximum salary threshold.
     * 
     * @return the maximum salary
     */
    public double getMaxSalary() {
        return maxSalary;
    }

    /**
     * Sets the maximum salary threshold.
     * 
     * @param maxSalary the maximum salary to set
     */
    public void setMaxSalary(double maxSalary) {
        this.maxSalary = maxSalary;
    }

    /**
     * Gets the percentage increase.
     * 
     * @return the percentage increase
     */
    public double getPercentageIncrease() {
        return percentageIncrease;
    }

    /**
     * Sets the percentage increase.
     * 
     * @param percentageIncrease the percentage increase to set
     */
    public void setPercentageIncrease(double percentageIncrease) {
        this.percentageIncrease = percentageIncrease;
    }

    /**
     * Validates the salary range parameters.
     * Ensures minSalary is less than maxSalary and percentage is positive.
     * 
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return minSalary < maxSalary && percentageIncrease > 0;
    }

    /**
     * Calculates the new salary after applying the percentage increase.
     * 
     * @param currentSalary the current salary
     * @return the new salary after increase
     */
    public double calculateNewSalary(double currentSalary) {
        return currentSalary * (1 + percentageIncrease / 100.0);
    }

    /**
     * Checks if a salary falls within this range.
     * 
     * @param salary the salary to check
     * @return true if salary is >= minSalary and < maxSalary
     */
    public boolean isInRange(double salary) {
        return salary >= minSalary && salary < maxSalary;
    }

    @Override
    public String toString() {
        return "SalaryRange{" +
                "minSalary=" + minSalary +
                ", maxSalary=" + maxSalary +
                ", percentageIncrease=" + percentageIncrease +
                '}';
    }
}
