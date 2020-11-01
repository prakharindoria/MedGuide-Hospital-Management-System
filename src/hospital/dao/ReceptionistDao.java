/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.dao;

import hospital.dbutil.DBConnection;
import hospital.pojo.EmpPojo;
import hospital.pojo.UserDetails;
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
public class ReceptionistDao {
    public static boolean addReceptionist(UserDetails user) throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry="insert into users values(?,?,?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(qry);
        ps.setString(1,user.getUserid());
        ps.setString(2,user.getUserName());
        ps.setString(3,user.getEmpId());
        ps.setString(4,user.getPassword());
        ps.setString(5,user.getUserType());
        ps.setString(6,user.getStatus());
        
        int x=ps.executeUpdate();
        return x>0;
    
    }
    

    public static ArrayList<EmpPojo> getAllRecep() throws SQLException{
    Connection conn =DBConnection.getConnection();
    Statement st=conn.createStatement();
    ResultSet rs=st.executeQuery("Select * from employees where role='RECEPTIONIST'");
    ArrayList<EmpPojo> empList=new ArrayList<>();
    while(rs.next()){
    EmpPojo e=new EmpPojo();
    e.setEmpid(rs.getString(1));
    e.setEmpname(rs.getString(2));
    e.setJob(rs.getString(3));
    e.setSalary(rs.getDouble(4));
    empList.add(e); 
    }
    return empList;
   }
    

     public static ArrayList<String> getAllRecepName()throws SQLException{
        ArrayList<String> RecepName = new ArrayList<>();
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery("select empname from employees where role='RECEPTIONIST'");
        while(rs.next())
        {
            RecepName.add(rs.getString(1));
        }
        return RecepName;
    }
     

     public static ArrayList<String> getAllRecepId()throws SQLException {
        ArrayList<String> RecepId = new ArrayList<>();
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery("select empid from employees where role='RECEPTIONIST'");
        while(rs.next())
        {
            RecepId.add(rs.getString(1));
        }
        return RecepId;
    }
    
}
