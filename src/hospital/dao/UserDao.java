package hospital.dao;

import hospital.dbutil.DBConnection;
import hospital.pojo.User; // Assuming this is hospital.pojo.User based on usage
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.sql.Statement; // To be removed
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());
    private static final String RECEPTIONIST_USER_TYPE = "RECEPTIONIST";

    public static String validateUser(User user) throws SQLException {
        String username = null;
        // Query selects 'username' from 'Users' table based on userid, password, and usertype.
        String sql = "SELECT username FROM Users WHERE userid = ? AND password = ? AND usertype = ?";

        // LOGGER.log(Level.INFO, "Validating user: {0}", user.getUserid()); // Optional: log user being validated

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserid());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getUserType());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    username = rs.getString("username");
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error validating user: " + user.getUserid(), ex);
            throw ex;
        }
        return username;
    }

    public static boolean changePassword(String userid, String pwd) throws SQLException {
        // Corrected SQL: "SET password = ? WHERE userid = ?"
        String sql = "UPDATE users SET password = ? WHERE userid = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pwd);
            ps.setString(2, userid);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error changing password for user: " + userid, ex);
            throw ex;
        }
    }

    public static HashMap<String, String> getReceptionistList() throws SQLException {
        HashMap<String, String> receptionistList = new HashMap<>();
        // Query selects 'userid' and 'username' from 'users' table for receptionists.
        String sql = "SELECT userid, username FROM users WHERE usertype = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, RECEPTIONIST_USER_TYPE);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    receptionistList.put(rs.getString("userid"), rs.getString("username"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error fetching receptionist list from users table", ex);
            throw ex;
        }
        return receptionistList;
    }

    // Methods mentioned in the prompt but not present in the original file:
    // getUserList(), DeleteUser(), getAllReceptionist() (variant of getReceptionistList?), getAllDoctors()
    // These would need to be implemented if required.
}
