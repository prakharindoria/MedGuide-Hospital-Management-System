package hospital.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class DBConnection {

    private static Connection conn; //As Needed in static block...

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//LAPTOP-Q82125JL:1521/xe", "med", "admin");
            JOptionPane.showMessageDialog(null, "Connection done successfully!");
        } catch (ClassNotFoundException cnfe) {
            JOptionPane.showMessageDialog(null, "Cannot load the driver", "Error", JOptionPane.ERROR_MESSAGE);
            cnfe.printStackTrace();
        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(null, "Problem in DataBase", "Error", JOptionPane.ERROR_MESSAGE);
            sqlex.printStackTrace();
        }

    }

    public static Connection getConnection() {
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                JOptionPane.showMessageDialog(null, "Connection Closed Successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(null, "Problem in connection", "Error", JOptionPane.ERROR_MESSAGE);
            sqlex.printStackTrace();

        }

        //When we make static method ? When every member is static OR When there is no instance member.
    }
}
