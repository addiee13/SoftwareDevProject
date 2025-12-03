package com.companyz.ems.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.companyz.ems.exception.DatabaseException;
import com.companyz.ems.model.PayStatement;
import com.companyz.ems.util.DatabaseConnection;

/**
 * Repository class for PayStatement entity.
 * Handles all database operations related to pay statements.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class PayStatementRepository implements Repository<PayStatement> {

    private final DatabaseConnection dbConnection;

    /**
     * Constructor that initializes the database connection.
     */
    public PayStatementRepository() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    /**
     * Finds a pay statement by statement ID.
     * 
     * @param id the statement ID
     * @return the PayStatement object, or null if not found
     * @throws DatabaseException if a database error occurs
     */
    @Override
    public PayStatement findById(int id) throws DatabaseException {
        String sql = "SELECT * FROM paystatement WHERE statementId = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractPayStatementFromResultSet(rs);
            }
            return null;
            
        } catch (SQLException e) {
            throw new DatabaseException("Error finding pay statement by ID: " + id, e);
        }
    }

    /**
     * Retrieves all pay statements.
     * 
     * @return list of all pay statements
     * @throws DatabaseException if a database error occurs
     */
    @Override
    public List<PayStatement> findAll() throws DatabaseException {
        String sql = "SELECT * FROM paystatement";
        List<PayStatement> statements = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                statements.add(extractPayStatementFromResultSet(rs));
            }
            
            return statements;
            
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving all pay statements", e);
        }
    }

    /**
     * Finds all pay statements for a specific employee.
     * 
     * @param empId the employee ID
     * @return list of pay statements for the employee
     * @throws DatabaseException if a database error occurs
     */
    public List<PayStatement> findByEmployeeId(int empId) throws DatabaseException {
        String sql = "SELECT * FROM paystatement WHERE empId = ? ORDER BY payDate DESC";
        List<PayStatement> statements = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                statements.add(extractPayStatementFromResultSet(rs));
            }
            
            return statements;
            
        } catch (SQLException e) {
            throw new DatabaseException("Error finding pay statements for employee: " + empId, e);
        }
    }

    /**
     * Finds pay statements for a specific month and year.
     * 
     * @param month the month (1-12)
     * @param year the year
     * @return list of pay statements for the specified month
     * @throws DatabaseException if a database error occurs
     */
    public List<PayStatement> findByMonthAndYear(int month, int year) throws DatabaseException {
        String sql = "SELECT * FROM paystatement WHERE MONTH(payDate) = ? AND YEAR(payDate) = ?";
        List<PayStatement> statements = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                statements.add(extractPayStatementFromResultSet(rs));
            }
            
            return statements;
            
        } catch (SQLException e) {
            throw new DatabaseException("Error finding pay statements for month/year: " + month + "/" + year, e);
        }
    }

    /**
     * Gets total pay by job title for a specific month and year.
     * 
     * @param month the month (1-12)
     * @param year the year
     * @return map of job title to total pay amount
     * @throws DatabaseException if a database error occurs
     */
    public Map<String, Double> getTotalPayByJobTitle(int month, int year) throws DatabaseException {
        String sql = "SELECT e.jobTitle, SUM(p.amount) as totalPay " +
                     "FROM paystatement p " +
                     "JOIN employee e ON p.empId = e.empId " +
                     "WHERE MONTH(p.payDate) = ? AND YEAR(p.payDate) = ? " +
                     "GROUP BY e.jobTitle " +
                     "ORDER BY e.jobTitle";
        
        Map<String, Double> payByJobTitle = new HashMap<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String jobTitle = rs.getString("jobTitle");
                double totalPay = rs.getDouble("totalPay");
                payByJobTitle.put(jobTitle, totalPay);
            }
            
            return payByJobTitle;
            
        } catch (SQLException e) {
            throw new DatabaseException("Error calculating total pay by job title", e);
        }
    }

    /**
     * Gets total pay by division for a specific month and year.
     * 
     * @param month the month (1-12)
     * @param year the year
     * @return map of division to total pay amount
     * @throws DatabaseException if a database error occurs
     */
    public Map<String, Double> getTotalPayByDivision(int month, int year) throws DatabaseException {
        String sql = "SELECT e.division, SUM(p.amount) as totalPay " +
                     "FROM paystatement p " +
                     "JOIN employee e ON p.empId = e.empId " +
                     "WHERE MONTH(p.payDate) = ? AND YEAR(p.payDate) = ? " +
                     "GROUP BY e.division " +
                     "ORDER BY e.division";
        
        Map<String, Double> payByDivision = new HashMap<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String division = rs.getString("division");
                double totalPay = rs.getDouble("totalPay");
                payByDivision.put(division, totalPay);
            }
            
            return payByDivision;
            
        } catch (SQLException e) {
            throw new DatabaseException("Error calculating total pay by division", e);
        }
    }

    /**
     * Saves a new pay statement to the database.
     * 
     * @param payStatement the pay statement to save
     * @throws DatabaseException if a database error occurs
     */
    @Override
    public void save(PayStatement payStatement) throws DatabaseException {
        String sql = "INSERT INTO paystatement (empId, amount, payDate, payPeriod) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, payStatement.getEmpId());
            stmt.setDouble(2, payStatement.getAmount());
            stmt.setDate(3, Date.valueOf(payStatement.getPayDate()));
            stmt.setString(4, payStatement.getPayPeriod());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Creating pay statement failed, no rows affected");
            }
            
            // Get the generated statement ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payStatement.setStatementId(generatedKeys.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            throw new DatabaseException("Error saving pay statement", e);
        }
    }

    /**
     * Updates an existing pay statement in the database.
     * 
     * @param payStatement the pay statement to update
     * @throws DatabaseException if a database error occurs
     */
    @Override
    public void update(PayStatement payStatement) throws DatabaseException {
        String sql = "UPDATE paystatement SET empId = ?, amount = ?, payDate = ?, payPeriod = ? " +
                     "WHERE statementId = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, payStatement.getEmpId());
            stmt.setDouble(2, payStatement.getAmount());
            stmt.setDate(3, Date.valueOf(payStatement.getPayDate()));
            stmt.setString(4, payStatement.getPayPeriod());
            stmt.setInt(5, payStatement.getStatementId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Updating pay statement failed, statement not found: " + 
                                          payStatement.getStatementId());
            }
            
        } catch (SQLException e) {
            throw new DatabaseException("Error updating pay statement", e);
        }
    }

    /**
     * Deletes a pay statement by ID.
     * 
     * @param id the statement ID to delete
     * @throws DatabaseException if a database error occurs
     */
    @Override
    public void delete(int id) throws DatabaseException {
        String sql = "DELETE FROM paystatement WHERE statementId = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Deleting pay statement failed, statement not found: " + id);
            }
            
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting pay statement", e);
        }
    }

    /**
     * Extracts a PayStatement object from a ResultSet.
     * 
     * @param rs the ResultSet containing pay statement data
     * @return the PayStatement object
     * @throws SQLException if a database error occurs
     */
    private PayStatement extractPayStatementFromResultSet(ResultSet rs) throws SQLException {
        Date sqlDate = rs.getDate("payDate");
        LocalDate payDate = sqlDate != null ? sqlDate.toLocalDate() : null;
        
        return new PayStatement(
            rs.getInt("statementId"),
            rs.getInt("empId"),
            rs.getDouble("amount"),
            payDate,
            rs.getString("payPeriod")
        );
    }
}
