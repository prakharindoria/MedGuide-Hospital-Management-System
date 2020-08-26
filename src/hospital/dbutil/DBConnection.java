/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    //In this class we will Load Driver in static block.
    private static Connection conn;//so that it runs only once hence static block is used
    static{
        try{
              Class.forName("oracle.jdbc.OracleDriver");
              conn=DriverManager.getConnection("jdbc:oracle:thin:@//Prakhar:1521/xe","system","admin");//lsnrctl command in command promt to check services of  computer
              JOptionPane.showMessageDialog(null,"Connection done successfully!");
           }
        catch(ClassNotFoundException cnfe){
           JOptionPane.showMessageDialog(null,"Cannot load the driver"+cnfe);
           cnfe.printStackTrace();
          }
        catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null,"Problem in DB"+sqlex);
           sqlex.printStackTrace();
        }
    
    }
    
    public static Connection getConnection(){//Because Connection is static
    return conn;
    }
    //When we make static ?
    //Ans:   1.When every member is static.    //2. Whene there is no instance member.
    public static void closeConnection(){
          try{
              if(conn!=null){
          conn.close();
          JOptionPane.showMessageDialog(null, "Connection Closed Successfully");
              }
          }
          catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "Problem in connection"+sqlex);
           sqlex.printStackTrace();
          }
    
    }
}

