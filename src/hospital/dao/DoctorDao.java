/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.dao;

import hospital.dbutil.DBConnection;
import hospital.pojo.DocPojo;
import hospital.pojo.EmpPojo;
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
public class DoctorDao {

    public static ArrayList<DocPojo> getDoctorsDetail() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from doctors");
        ArrayList<DocPojo> docList = new ArrayList<>();
        while (rs.next()) {
            DocPojo e = new DocPojo();
            e.setDocid(rs.getString(2));
            e.setUserid(rs.getString(1));
            e.setQualification(rs.getString(3));
            e.setSpecility(rs.getString(4));
            //e.setStatus(rs.getString(5));

            docList.add(e);
        }
        return docList;
    }

    public static boolean addDoc(DocPojo e) throws SQLException {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("insert into doctors values(?,?,?,?,?)");
        ps.setString(1, e.getUserid());
        ps.setString(2, e.getDocid());
        ps.setString(3, e.getQualification());
        ps.setString(4, e.getSpecility());
        ps.setString(5, e.getStatus());
        int x = ps.executeUpdate();
        return x > 0;
    }
    public static ArrayList<String> getAllDoctorsId() throws SQLException {
        ArrayList<String> docId = new ArrayList<>();
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery("select empid from employees where role='DOCTOR'");
        while (rs.next()) {
            docId.add(rs.getString(1));
        }
        return docId;
    }
    
    
    
    /*public static ArrayList<DocPojo> getAllDoctorId() throws SQLException{
     Connection conn =DBConnection.getConnection();
     Statement st=conn.createStatement();
     ResultSet rs=st.executeQuery("Select doctorid from doctors");
     ArrayList<DocPojo> docList=new ArrayList<>();
     while(rs.next()){
     DocPojo e=new DocPojo();
     e.setDocid(rs.getString(1));
    
    
     docList.add(e); 
     }
     return docList;
     }*/
}
