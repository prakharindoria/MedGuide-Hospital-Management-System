package hospital.dao;

import hospital.dbutil.DBConnection;
import hospital.pojo.AppointmentPojo; // Assuming this is used correctly by getPatientDetail, though it seems specific
import hospital.pojo.PatientPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Will be removed where PreparedStatement is used
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientDao {

    private static final Logger LOGGER = Logger.getLogger(PatientDao.class.getName());

    public static String getNewPatId() throws SQLException {
        String defaultNewId = "P101";
        String sql = "SELECT MAX(p_id) FROM patient";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String currentMaxId = rs.getString(1);
                if (currentMaxId != null) {
                    try {
                        // Expecting IDs like "P123"
                        if (currentMaxId.startsWith("P") && currentMaxId.length() > 1) {
                            String numericPart = currentMaxId.substring(1);
                            int num = Integer.parseInt(numericPart);
                            return "P" + (num + 1);
                        } else {
                            LOGGER.log(Level.WARNING, "Existing max p_id ''{0}'' does not follow the expected 'P' + number pattern. Defaulting to {1}.", new Object[]{currentMaxId, defaultNewId});
                            return defaultNewId; // Pattern mismatch
                        }
                    } catch (NumberFormatException nfe) {
                        LOGGER.log(Level.WARNING, "Could not parse numeric part of p_id ''{0}''. Defaulting to {1}.", new Object[]{currentMaxId, defaultNewId});
                        return defaultNewId; // Parsing error
                    }
                } else {
                    // No existing IDs, table might be empty or MAX(p_id) returned null
                    return defaultNewId;
                }
            } else {
                // Should not happen with MAX() if table exists, but as a fallback
                return defaultNewId;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error generating new Patient ID using MAX(p_id)", ex);
            throw ex;
        }
    }

    // This method populates AppointmentPojo with limited patient data.
    // Consider renaming or using PatientPojo if more details are needed consistently.
    public static ArrayList<AppointmentPojo> getPatientDetail() throws SQLException {
        ArrayList<AppointmentPojo> patList = new ArrayList<>();
        String sql = "SELECT p_id, f_name, opd FROM patient"; // Assuming f_name is pname, adjust if mapping is different
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AppointmentPojo patientAppointment = new AppointmentPojo();
                patientAppointment.setPatid(rs.getString("p_id"));
                patientAppointment.setPname(rs.getString("f_name")); // Assuming f_name is what's meant for pname
                patientAppointment.setOpd(rs.getString("opd"));
                patList.add(patientAppointment);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching patient details for appointments", ex);
            throw ex;
        }
        return patList;
    }

    public static PatientPojo getPatById(String id) throws SQLException {
        PatientPojo patient = null;
        String sql = "SELECT * FROM patient WHERE p_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    patient = new PatientPojo();
                    patient.setPid(rs.getString("p_id"));
                    patient.setFname(rs.getString("f_name"));
                    patient.setSname(rs.getString("s_name"));
                    patient.setAge(rs.getInt("age"));
                    patient.setOpd(rs.getString("opd"));
                    patient.setGender(rs.getString("gender"));
                    patient.setMstatus(rs.getString("m_status"));
                    patient.setPdate(rs.getDate("p_date"));
                    patient.setAddress(rs.getString("address"));
                    patient.setCity(rs.getString("city"));
                    patient.setMno(rs.getString("phone_no")); // Assuming column name is phone_no
                    patient.setDocid(rs.getString("doctorid"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching patient by ID: " + id, ex);
            throw ex;
        }
        return patient;
    }

    public static ArrayList<PatientPojo> getAllPatientDetail() throws SQLException {
        ArrayList<PatientPojo> patList = new ArrayList<>();
        String sql = "SELECT * FROM patient";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PatientPojo patient = new PatientPojo();
                patient.setPid(rs.getString("p_id"));
                patient.setFname(rs.getString("f_name"));
                patient.setSname(rs.getString("s_name"));
                patient.setAge(rs.getInt("age"));
                patient.setOpd(rs.getString("opd"));
                patient.setGender(rs.getString("gender"));
                patient.setMstatus(rs.getString("m_status"));
                patient.setPdate(rs.getDate("p_date"));
                patient.setAddress(rs.getString("address"));
                patient.setCity(rs.getString("city"));
                patient.setMno(rs.getString("phone_no")); // Assuming column name is phone_no
                patient.setDocid(rs.getString("doctorid"));
                patList.add(patient);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching all patient details", ex);
            throw ex;
        }
        return patList;
    }

    public static boolean addPatient(PatientPojo p) throws SQLException {
        String sql = "INSERT INTO patient (p_id, f_name, s_name, age, opd, gender, m_status, p_date, address, city, phone_no, doctorid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getPid());
            ps.setString(2, p.getFname());
            ps.setString(3, p.getSname());
            ps.setInt(4, p.getAge());
            ps.setString(5, p.getOpd());
            ps.setString(6, p.getGender());
            ps.setString(7, p.getMstatus());
            ps.setDate(8, p.getPdate());
            ps.setString(9, p.getAddress());
            ps.setString(10, p.getCity());
            ps.setString(11, p.getMno());
            ps.setString(12, p.getDocid());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error adding patient: " + p.getPid(), ex);
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

    public static boolean updatePatient(PatientPojo p) throws SQLException {
        // Corrected SQL: Added WHERE clause and ensure parameter order matches
        String sql = "UPDATE patient SET f_name=?, s_name=?, age=?, opd=?, gender=?, m_status=?, p_date=?, address=?, city=?, phone_no=?, doctorid=? WHERE p_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getFname());
            ps.setString(2, p.getSname());
            ps.setInt(3, p.getAge());
            ps.setString(4, p.getOpd());
            ps.setString(5, p.getGender());
            ps.setString(6, p.getMstatus());
            ps.setDate(7, p.getPdate());
            ps.setString(8, p.getAddress());
            ps.setString(9, p.getCity());
            ps.setString(10, p.getMno());
            ps.setString(11, p.getDocid());
            ps.setString(12, p.getPid()); // WHERE clause parameter
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error updating patient: " + p.getPid(), ex);
            throw ex;
        }
    }

    public static boolean deletePatById(String id) throws SQLException {
        String sql = "DELETE FROM patient WHERE p_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error deleting patient by ID: " + id, ex);
            throw ex;
        }
    }
}
