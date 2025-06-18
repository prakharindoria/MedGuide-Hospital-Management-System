package hospital.dao;

import hospital.dbutil.DBConnection;
import hospital.pojo.EmpPojo;
import hospital.pojo.UserDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.sql.Statement; // To be removed
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceptionistDao {

    private static final Logger LOGGER = Logger.getLogger(ReceptionistDao.class.getName());
    private static final String RECEPTIONIST_ROLE = "RECEPTIONIST";

    public static boolean addReceptionist(UserDetails user) throws SQLException {
        // Assuming table 'users' has columns: userid, username, empid, password, usertype
        String sql = "INSERT INTO users (userid, username, empid, password, usertype) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserid());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getEmpId());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getUserType()); // Should typically be RECEPTIONIST_ROLE or validated

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error adding receptionist: " + user.getUserid(), ex);
            throw ex;
        }
    }

    public static ArrayList<EmpPojo> getAllRecep() throws SQLException {
        ArrayList<EmpPojo> empList = new ArrayList<>();
        // Selecting specific columns from employees table for receptionists
        String sql = "SELECT empid, empname, job, salary FROM employees WHERE job = ?"; // Assuming 'job' column stores role

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, RECEPTIONIST_ROLE);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EmpPojo e = new EmpPojo();
                    e.setEmpid(rs.getString("empid"));
                    e.setEmpname(rs.getString("empname"));
                    e.setJob(rs.getString("job")); // This will be 'RECEPTIONIST'
                    e.setSalary(rs.getDouble("salary"));
                    empList.add(e);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching all receptionists from employees table", ex);
            throw ex;
        }
        return empList;
    }

    public static ArrayList<String> getAllRecepName() throws SQLException {
        ArrayList<String> recepNameList = new ArrayList<>();
        // Query assumes 'job' column in 'employees' table stores the role
        String sql = "SELECT empname FROM employees WHERE job = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, RECEPTIONIST_ROLE);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recepNameList.add(rs.getString("empname"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching all receptionist names", ex);
            throw ex;
        }
        return recepNameList;
    }

    public static ArrayList<String> getAllRecepId() throws SQLException {
        ArrayList<String> recepIdList = new ArrayList<>();
        // Query assumes 'job' column in 'employees' table stores the role
        String sql = "SELECT empid FROM employees WHERE job = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, RECEPTIONIST_ROLE);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recepIdList.add(rs.getString("empid"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching all receptionist IDs", ex);
            throw ex;
        }
        return recepIdList;
    }

    // Methods mentioned in the prompt but not present in the original file:
    // RemoveRecep(), getReceptionistList(), updateReceptionistByName(), updateReceptionist()
    // These would need to be implemented if required.
}
