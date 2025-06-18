package hospital.dao;

import hospital.dbutil.DBConnection;
import hospital.pojo.DocPojo;
// import hospital.pojo.EmpPojo; // EmpPojo is not used in this file.
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.sql.Statement; // Will be removed as PreparedStatement is used.
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoctorDao {

    private static final Logger LOGGER = Logger.getLogger(DoctorDao.class.getName());

    public static ArrayList<DocPojo> getDoctorsDetail() throws SQLException {
        ArrayList<DocPojo> docList = new ArrayList<>();
        String sql = "SELECT userid, doctorid, qualification, speciality, status FROM doctors"; // Explicitly list columns

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DocPojo doctor = new DocPojo();
                doctor.setUserid(rs.getString("userid"));
                doctor.setDocid(rs.getString("doctorid"));
                doctor.setQualification(rs.getString("qualification"));
                doctor.setSpecility(rs.getString("speciality")); // Corrected Pojo setter name if it's 'setSpecility'
                doctor.setStatus(rs.getString("status")); // Assuming DocPojo has setStatus and status column exists
                docList.add(doctor);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching all doctor details", ex);
            throw ex;
        }
        return docList;
    }

    public static boolean addDoc(DocPojo e) throws SQLException {
        // Assuming table 'doctors' has columns in order: userid, doctorid, qualification, speciality, status
        String sql = "INSERT INTO doctors (userid, doctorid, qualification, speciality, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getUserid());
            ps.setString(2, e.getDocid());
            ps.setString(3, e.getQualification());
            ps.setString(4, e.getSpecility()); // Corrected Pojo getter name if it's 'getSpecility'
            ps.setString(5, e.getStatus());   // Assuming DocPojo has getStatus

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error adding doctor: " + e.getDocid(), ex);
            throw ex;
        }
    }

    public static ArrayList<String> getAllDoctorsId() throws SQLException {
        ArrayList<String> docIdList = new ArrayList<>();
        String sql = "SELECT doctorid FROM doctors";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                docIdList.add(rs.getString("doctorid"));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching all doctor IDs", ex);
            throw ex;
        }
        return docIdList;
    }
    
    // Methods mentioned in the prompt but not present in the original file:
    // validate(), getDoctorDetailsById(), deleteDoctor(), updateDoctor(), getNewId()
    // If these methods are required, they would need to be implemented.
    // For example, a getDoctorDetailsById(String docId) could look like:
    /*
    public static DocPojo getDoctorDetailsById(String doctorId) throws SQLException {
        DocPojo doctor = null;
        String sql = "SELECT userid, doctorid, qualification, speciality, status FROM doctors WHERE doctorid = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    doctor = new DocPojo();
                    doctor.setUserid(rs.getString("userid"));
                    doctor.setDocid(rs.getString("doctorid"));
                    doctor.setQualification(rs.getString("qualification"));
                    doctor.setSpecility(rs.getString("speciality"));
                    doctor.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching doctor by ID: " + doctorId, ex);
            throw ex;
        }
        return doctor;
    }
    */
}
