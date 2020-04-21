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
public class AppointmentPojo {

    @Override
    public String toString() {
        return "AppointmentPojo{" + "patid=" + patid + ", pname=" + pname + ", opd=" + opd + '}';
    }

    public String getPatid() {
        return patid;
    }

    public void setPatid(String patid) {
        this.patid = patid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getOpd() {
        return opd;
    }

    public void setOpd(String opd) {
        this.opd = opd;
    }
    private String patid;
    private String pname;
    private String opd;
    
}
