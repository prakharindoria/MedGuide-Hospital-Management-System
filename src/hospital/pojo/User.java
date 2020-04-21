/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.pojo;

/**
 *
 * @author Admin
 */
public class User {

    @Override
    public String toString() {
        return "User{" + "userid=" + userid + ", password=" + password + ", userType=" + userType + '}';
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    private String userid;
    private String password;
    private String userType;
    
    
}
