package hospital.dao;

import hospital.dbutil.DBConnection;
import hospital.pojo.EmpPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.sql.Statement; // To be removed
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmpDao {

    private static final Logger LOGGER = Logger.getLogger(EmpDao.class.getName());

    public static String getNewId() throws SQLException {
        String newEmpId = "e101"; // Default if no records
        String sql = "SELECT COUNT(*) AS emp_count FROM employees";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt("emp_count");
                newEmpId = "E" + (count + 101); // Standardized to E and ensure calculation is correct
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error generating new Employee ID", ex);
            throw ex;
        }
        return newEmpId;
    }

    public static boolean addEmp(EmpPojo e) throws SQLException {
        String sql = "INSERT INTO employees (empid, empname, job, salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getEmpid());
            ps.setString(2, e.getEmpname());
            ps.setString(3, e.getJob());
            ps.setDouble(4, e.getSalary());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error adding employee: " + e.getEmpid(), ex);
            throw ex;
        }
    }

    public static ArrayList<EmpPojo> getAllEmp() throws SQLException {
        ArrayList<EmpPojo> empList = new ArrayList<>();
        String sql = "SELECT empid, empname, job, salary FROM employees";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EmpPojo e = new EmpPojo();
                e.setEmpid(rs.getString("empid"));
                e.setEmpname(rs.getString("empname"));
                e.setJob(rs.getString("job"));
                e.setSalary(rs.getDouble("salary"));
                empList.add(e);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching all employees", ex);
            throw ex;
        }
        return empList;
    }

    // This method fetches all employees and puts them into a map.
    // Consider if a method getEmpById(String empId) returning EmpPojo is needed.
    public static HashMap<String, EmpPojo> getEmpById() throws SQLException {
        HashMap<String, EmpPojo> map = new HashMap<>();
        String sql = "SELECT empid, empname, job, salary FROM employees";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EmpPojo e = new EmpPojo();
                e.setEmpid(rs.getString("empid"));
                e.setEmpname(rs.getString("empname"));
                e.setJob(rs.getString("job"));
                e.setSalary(rs.getDouble("salary"));
                map.put(rs.getString("empid"), e);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching all employees into map", ex);
            throw ex;
        }
        return map;
    }

    // Deletes from 'users' and 'employees'. Consider transactional integrity.
    public static boolean deleteEmp(String id) throws SQLException {
        String sqlUsers = "DELETE FROM users WHERE empid = ?";
        String sqlEmployees = "DELETE FROM employees WHERE empid = ?";
        boolean usersDeleted = false;
        boolean employeesDeleted = false;

        try (Connection conn = DBConnection.getConnection()) {
            // Operation 1: Delete from users
            try (PreparedStatement psUsers = conn.prepareStatement(sqlUsers)) {
                psUsers.setString(1, id);
                usersDeleted = psUsers.executeUpdate() > 0;
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error deleting employee from users table: " + id, ex);
                throw ex; // Or handle more gracefully depending on desired atomicity
            }

            // Operation 2: Delete from employees
            try (PreparedStatement psEmployees = conn.prepareStatement(sqlEmployees)) {
                psEmployees.setString(1, id);
                employeesDeleted = psEmployees.executeUpdate() > 0;
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error deleting employee from employees table: " + id, ex);
                // If users was deleted, this introduces inconsistency. Transaction recommended.
                throw ex;
            }
            // This return logic might need adjustment based on whether partial success is acceptable.
            // Current logic: both must be successful if no exception is thrown.
            // However, if users table doesn't have the empid, usersDeleted will be false.
            // A more robust check might be needed, or rely on foreign keys if they exist.
            return employeesDeleted; // Or usersDeleted && employeesDeleted if user must exist
        } catch (SQLException ex) {
            // This top-level catch is for connection errors or re-thrown errors from inner blocks.
            LOGGER.log(Level.SEVERE, "Error during delete employee operations for ID: " + id, ex);
            throw ex;
        }
    }

    public static boolean updateEmployee(EmpPojo emp) throws SQLException {
        // Assuming 'role' in original query meant 'job'
        String sql = "UPDATE employees SET empname = ?, job = ?, salary = ? WHERE empid = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getEmpname());
            ps.setString(2, emp.getJob());
            ps.setDouble(3, emp.getSalary());
            ps.setString(4, emp.getEmpid());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error updating employee: " + emp.getEmpid(), ex);
            throw ex;
        }
    }

    public static HashMap<String, String> getNonRegisteredReceptionistList() throws SQLException {
        HashMap<String, String> receptionistList = new HashMap<>();
        // Query assumes 'RECEPTIONIST' is the exact string in usertype column
        String sql = "SELECT userid, username FROM users WHERE usertype = 'RECEPTIONIST'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                receptionistList.put(rs.getString("userid"), rs.getString("username"));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching non-registered receptionists", ex);
            throw ex;
        }
        return receptionistList;
    }
    
    // This method seems redundant if deleteEmp is supposed to handle deletion from 'employees' as well.
    // If it's specifically for only 'employees' table, its existence is fine.
    public static boolean deleteEmpById(String id) throws SQLException {
       String sql = "DELETE FROM employees WHERE empid = ?";
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
           ps.setString(1, id);
           return ps.executeUpdate() > 0;
       } catch (SQLException ex) {
           LOGGER.log(Level.SEVERE, "Error deleting employee from employees table by ID: " + id, ex);
           throw ex;
       }
    }
}
