package com.companyz.ems.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.companyz.ems.exception.DatabaseException;
import com.companyz.ems.model.Employee;
import com.companyz.ems.model.SalaryRange;
import com.companyz.ems.util.DatabaseConnection;

/**
 * Repository class for Employee entity.
 * Handles all database operations related to employees.
 * 
 * @author Company Z Development Team
 * @version 1.0
 */
public class EmployeeRepository implements Repository<Employee> {

    private final DatabaseConnection dbConnection;

    /**
     * Constructor that initializes the database connection.
     */
    public EmployeeRepository() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    /**
     * Finds an employee by employee ID.
     * 
     * @param id the employee ID
     * @return the Employee object, or null if not found
     * @throws DatabaseException if a database error occurs
     */
    @Override
    public Employee findById(int id) throws DatabaseException {
        String sql = "SELECT * FROM employee WHERE empId = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractEmployeeFromResultSet(rs);
            }
            return null;
            
        } catch (SQLException e) {
            throw new DatabaseException("Error finding employee by ID: " + id, e);
        }
    }

    /**
     * Alias for findById to match search by employee ID requirement.
     * 
     * @param empId the employee ID
     * @return the Employee object, or null if not found
     * @throws DatabaseException if a database error occurs
     */
    public Employee findByEmpId(int empId) throws DatabaseException {
        return findById(empId);
    }

    /**
     * Finds employees by name (searches both first and last name).
     * 
     * @param name the name to search for
     * @return list of matching employees
     * @throws DatabaseException if a database error occurs
     */
    public List<Employee> findByName(String name) throws DatabaseException {
        String sql = "SELECT * FROM employee WHERE firstName LIKE ? OR lastName LIKE ?";
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + name + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                employees.add(extractEmployeeFromResultSet(rs));
            }
            
            return employees;
            
        } catch (SQLException e) {
            throw new DatabaseException("Error finding employee by name: " + name, e);
        }
    }

    /**
     * Finds an employee by SSN.
     * 
     * @param ssn the SSN to search for (9 digits)
     * @return the Employee object, or null if not found
     * @throws DatabaseException if a database error occurs
     */
    public Employee findBySsn(String ssn) throws DatabaseException {
        String sql = "SELECT * FROM employee WHERE ssn = ?";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ssn);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractEmployeeFromResultSet(rs);
            }
            return null;
            
        } catch (SQLException e) {
            throw new DatabaseException("Error finding employee by SSN", e);
        }
    }

    /**
     * Retrieves all employees.
     * 
     * @return list of all employees
     * @throws DatabaseException if a database error occurs
     */
    @Override
    public List<Employee> findAll() throws DatabaseException {
        String sql = "SELECT * FROM employee";
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                employees.add(extractEmployeeFromResultSet(rs));
            }
            
            return employees;
            
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving all employees", e);
        }
    }

    /**
     * Retrieves all full-time employees.
     * 
     * @return list of full-time employees
     * @throws DatabaseException if a database error occurs
     */
    public List<Employee> findAllFullTime() throws DatabaseException {
        String sql = "SELECT * FROM employee WHERE employmentType = 'FULL_TIME'";
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                employees.add(extractEmployeeFromResultSet(rs));
            }
            
            return employees;
            
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving full-time employees", e);
        }
    }

    /**
     * Saves a new employee to the database.
     * 
     * @param employee the employee to save
     * @throws DatabaseException if a database error occurs
     */
    @Override
    public void save(Employee employee) throws DatabaseException {
        String sql = "INSERT INTO employee (firstName, lastName, ssn, jobTitle, division, salary, employmentType) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getSsn());
            stmt.setString(4, employee.getJobTitle());
            stmt.setString(5, employee.getDivision());
            stmt.setDouble(6, employee.getSalary());
            stmt.setString(7, employee.getEmploymentType());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new DatabaseException("Creating employee failed, no rows affected");
            }
            
            // Get the generated employee ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setEmpId(generatedKeys.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            throw new DatabaseException("Error saving employee", e);
        }
    }

    /**
     * Updates an existing employee in the database.
     * Uses transaction to ensure data consistency.
     * 
     * @param employee the employee to update
     * @throws DatabaseException if a database error occurs
     */
    @Override
    public void update(Employee employee) throws DatabaseException {
        String sql = "UPDATE employee SET firstName = ?, lastName = ?, ssn = ?, jobTitle = ?, " +
                     "division = ?, salary = ?, employmentType = ? WHERE empId = ?";
        
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, employee.getFirstName());
                stmt.setString(2, employee.getLastName());
                stmt.setString(3, employee.getSsn());
                stmt.setString(4, employee.getJobTitle());
                stmt.setString(5, employee.getDivision());
                stmt.setDouble(6, employee.getSalary());
                stmt.setString(7, employee.getEmploymentType());
                stmt.setInt(8, employee.getEmpId());
                
                int affectedRows = stmt.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DatabaseException("Updating employee failed, employee not found: " + employee.getEmpId());
                }
                
                conn.commit();
            }
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new DatabaseException("Error rolling back transaction", ex);
                }
            }
            throw new DatabaseException("Error updating employee", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    // Log error but don't throw
                }
            }
        }
    }

    /**
     * Deletes an employee by ID.
     * Cascading delete will remove associated pay statements.
     * 
     * @param id the employee ID to delete
     * @throws DatabaseException if a database error occurs
     */
    @Override
    public void delete(int id) throws DatabaseException {
        String sql = "DELETE FROM employee WHERE empId = ?";
        
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                
                int affectedRows = stmt.executeUpdate();
                
                if (affectedRows == 0) {
                    throw new DatabaseException("Deleting employee failed, employee not found: " + id);
                }
                
                conn.commit();
            }
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new DatabaseException("Error rolling back transaction", ex);
                }
            }
            throw new DatabaseException("Error deleting employee", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    // Log error but don't throw
                }
            }
        }
    }

    /**
     * Updates salaries for employees within a specified salary range.
     * 
     * @param range the salary range with min, max, and percentage increase
     * @return the number of employees updated
     * @throws DatabaseException if a database error occurs
     */
    public int updateSalaryByRange(SalaryRange range) throws DatabaseException {
        String sql = "UPDATE employee SET salary = salary * (1 + ? / 100.0) " +
                     "WHERE salary >= ? AND salary < ?";
        
        Connection conn = null;
        try {
            conn = dbConnection.getConnection();
            conn.setAutoCommit(false);
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, range.getPercentageIncrease());
                stmt.setDouble(2, range.getMinSalary());
                stmt.setDouble(3, range.getMaxSalary());
                
                int affectedRows = stmt.executeUpdate();
                
                conn.commit();
                return affectedRows;
            }
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new DatabaseException("Error rolling back transaction", ex);
                }
            }
            throw new DatabaseException("Error updating salaries by range", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    // Log error but don't throw
                }
            }
        }
    }

    /**
     * Extracts an Employee object from a ResultSet.
     * 
     * @param rs the ResultSet containing employee data
     * @return the Employee object
     * @throws SQLException if a database error occurs
     */
    private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getInt("empId"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getString("ssn"),
            rs.getString("jobTitle"),
            rs.getString("division"),
            rs.getDouble("salary"),
            rs.getString("employmentType")
        );
    }
}
