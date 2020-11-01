/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.dao;

import hospital.dbutil.DBConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import hospital.pojo.User;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class UserDao {

    public static String validateUser(User user) throws SQLException {
        System.out.println(user);
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("Select username from Users where  userid=? and password=? and usertype=?");
        ps.setString(1, user.getUserid());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getUserType());
        ResultSet rs = ps.executeQuery();
        String username = null;
        if (rs.next()) {
            username = rs.getString(1);
        }
        return username;

    }

    public static boolean changePassword(String userid, String pwd) throws SQLException {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("update users set password=?,where userid=?");
        ps.setString(1, pwd);
        ps.setString(2, userid);
        return (ps.executeUpdate() != 0);
    }

    public static HashMap<String, String> getReceptionistList() throws SQLException {
        HashMap<String, String> receptionistList = new HashMap<>();
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery("select userid,username from users where usertype='RECEPTIONIST'");
        while (rs.next()) {
            receptionistList.put(rs.getString(1), rs.getString(2));
        }
        return receptionistList;
    }

}
