/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.dao;

import hospital.dbutil.DBConnection;
import hospital.pojo.AppointmentPojo;
import hospital.pojo.EmpPojo;
import hospital.pojo.PatientPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class PatientDao {

    public static String getNewPatId() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select count(*) from patient");
        if (rs.next()) {
            int num = Integer.parseInt(rs.getString(1));
            String newidis = (String) "p" + (num + 101);
            return newidis;
        } else {
            return "P101";
        }
    }

    public static ArrayList<AppointmentPojo> getPatientDetail() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from patient");
        ArrayList<AppointmentPojo> patList = new ArrayList<>();
        while (rs.next()) {
            AppointmentPojo e = new AppointmentPojo();
            e.setPatid(rs.getString(1));
            e.setPname(rs.getString(2));
            e.setOpd(rs.getString(5));
            patList.add(e);
        }
        return patList;
    }

    
    public static PatientPojo getPatById(String id) throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from patient where p_id='"+id+"'");
        PatientPojo e=null;
        while (rs.next()) {
            e = new PatientPojo();
            e.setPid(rs.getString(1));
            e.setFname(rs.getString(2));
            e.setSname(rs.getString(3));
            e.setAge(rs.getInt(4));
            e.setOpd(rs.getString(5));
            e.setGender(rs.getString(6));
            e.setMstatus(rs.getString(7));
            e.setPdate(rs.getDate(8));
            e.setAddress(rs.getString(9));
            e.setCity(rs.getString(10));
            e.setMno(rs.getString(11));
            e.setDocid(rs.getString(12));
            //e.setRefs(rs.getInt(13));
            
        }
        return e;
    }
    
    
    
    public static ArrayList<PatientPojo> getAllPatientDetail() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from patient");
        ArrayList<PatientPojo> patList = new ArrayList<>();
        while (rs.next()) {
            PatientPojo e = new PatientPojo();
            e.setPid(rs.getString(1));
            e.setFname(rs.getString(2));
            e.setSname(rs.getString(3));
            e.setAge(rs.getInt(4));
            e.setOpd(rs.getString(5));
            e.setGender(rs.getString(6));
            e.setMstatus(rs.getString(7));
            e.setPdate(rs.getDate(8));
            e.setAddress(rs.getString(9));
            e.setCity(rs.getString(10));
            e.setMno(rs.getString(11));
            e.setDocid(rs.getString(12));
            //e.setRefs(rs.getInt(13));
            patList.add(e);
        }
        return patList;
    }

    public static boolean addPatient(PatientPojo p) throws SQLException {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("insert into patient values(?,?,?,?,?,?,?,?,?,?,?,?)");
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
        return (ps.executeUpdate() != 0);
    }

    public static ArrayList<String> getAllDoctorsId() throws SQLException {
        ArrayList<String> docId = new ArrayList<>();
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery("select doctorid from doctors");
        while (rs.next()) {
            docId.add(rs.getString(1));
        }
        return docId;
    }

    public static boolean updatePatient(PatientPojo p) throws SQLException {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("update patient set p_id=?,f_name=?,s_name=?,age=?,opd=?,gender=?,m_status=?,p_date=?,address=?,city=?,phone_no=?,doctorid=?");
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
        return ps.executeUpdate() != 0;

    }
    
    
    public static boolean deletePatById(String id) throws SQLException {
       PreparedStatement ps = DBConnection.getConnection().prepareStatement("DELETE FROM patient WHERE p_id=?");
        ps.setString(1, String.valueOf(id));
        return ps.executeUpdate() !=0;
        
        
        
    }
    
    
    
   

}
