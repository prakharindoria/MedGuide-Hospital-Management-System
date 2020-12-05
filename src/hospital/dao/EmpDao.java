/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.dao;

import hospital.dbutil.DBConnection;
import hospital.pojo.EmpPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class EmpDao {

    public static String getNewId() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select count(*) from employees");
        if (rs.next()) {
            String a = rs.getString(1);
            System.out.println(a);
            int num = Integer.parseInt(a);
            String newidis = (String) "e" + (num + 100);
            return newidis;
        }
        return "e101";
    }

    public static boolean addEmp(EmpPojo e) throws SQLException {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("insert into employees values(?,?,?,?)");
        ps.setString(1, e.getEmpid());
        ps.setString(2, e.getEmpname());
        ps.setString(3, e.getJob());
        ps.setDouble(4, e.getSalary());
        int x = ps.executeUpdate();
        return x > 0;
    }

    public static ArrayList<EmpPojo> getAllEmp() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("Select * from employees");
        ArrayList<EmpPojo> empList = new ArrayList<>();
        while (rs.next()) {
            EmpPojo e = new EmpPojo();
            e.setEmpid(rs.getString(1));
            e.setEmpname(rs.getString(2));
            e.setJob(rs.getString(3));
            e.setSalary(rs.getDouble(4));
            empList.add(e);
        }
        return empList;
    }

    public static HashMap<String, EmpPojo> getEmpById() throws SQLException {
        HashMap<String, EmpPojo> map = new HashMap<String, EmpPojo>();
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery("select * from employees");
        while (rs.next()) {
            EmpPojo e = new EmpPojo();
            e.setEmpid(rs.getString(1));
            e.setEmpname(rs.getString(2));
            e.setJob(rs.getString(3));
            e.setSalary(rs.getDouble(4));
            map.put(rs.getString(1), e);
        }
        return map;
    }

    public static boolean deleteEmp(String id) throws SQLException {
        Connection conn=DBConnection.getConnection();
        try{
       PreparedStatement ps1 = DBConnection.getConnection().prepareStatement("DELETE FROM users WHERE empid=?");
       PreparedStatement ps2 = DBConnection.getConnection().prepareStatement("DELETE FROM employees WHERE empid=?");
        ps1.setString(1, String.valueOf(id));ps2.setString(1, String.valueOf(id));
        return ps1.executeUpdate() != 0 && ps2.executeUpdate()!=0;
        }catch(Exception ex){
        ex.printStackTrace();
        return false;
        
        }
    }

    public static boolean updateEmployee(EmpPojo emp) throws SQLException {
        PreparedStatement ps = DBConnection.getConnection().prepareStatement("update employees set empname=?,role=?,sal=? where empid=?");
        ps.setString(1, emp.getEmpname());
        ps.setString(2, emp.getJob());
        ps.setDouble(3, emp.getSalary());
        ps.setString(4, emp.getEmpid());
        return ps.executeUpdate() != 0;
    }

    public static HashMap<String, String> getNonRegisteredReceptionistList() throws SQLException {
        HashMap<String, String> receptionistList = new HashMap<>();
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery("select userid,username from users where usertype='RECEPTIONIST'");
        while (rs.next()) {
            receptionistList.put(rs.getString(1), rs.getString(2));
        }
        return receptionistList;
    }
    
    public static boolean deleteEmpById(String id) throws SQLException {
        Connection conn=DBConnection.getConnection();
       PreparedStatement ps = DBConnection.getConnection().prepareStatement("DELETE FROM employees WHERE empid=?");
        ps.setString(1, String.valueOf(id));
        return ps.executeUpdate() !=0;
        
        
        
    }
    
    
    
}
