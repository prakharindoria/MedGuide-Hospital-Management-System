/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hospital.pojo;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Admin
 */
public class PatientPojo {

    public PatientPojo(String pid, String fname, String sname, int age, String opd, String gender, String mstatus, Date pdate, String address, String city, String mno, String docid, int refs) {
        this.pid = pid;
        this.fname = fname;
        this.sname = sname;
        this.age = age;
        this.opd = opd;
        this.gender = gender;
        this.mstatus = mstatus;
        this.pdate = pdate;
        this.address = address;
        this.city = city;
        this.mno = mno;
        this.docid = docid;
        this.refs = refs;
    }

    public PatientPojo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.pid);
        hash = 61 * hash + Objects.hashCode(this.fname);
        hash = 61 * hash + Objects.hashCode(this.sname);
        hash = 61 * hash + this.age;
        hash = 61 * hash + Objects.hashCode(this.opd);
        hash = 61 * hash + Objects.hashCode(this.gender);
        hash = 61 * hash + Objects.hashCode(this.mstatus);
        hash = 61 * hash + Objects.hashCode(this.pdate);
        hash = 61 * hash + Objects.hashCode(this.address);
        hash = 61 * hash + Objects.hashCode(this.city);
        hash = 61 * hash + Objects.hashCode(this.mno);
        hash = 61 * hash + Objects.hashCode(this.docid);
        hash = 61 * hash + this.refs;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PatientPojo other = (PatientPojo) obj;
        if (!Objects.equals(this.pid, other.pid)) {
            return false;
        }
        if (!Objects.equals(this.fname, other.fname)) {
            return false;
        }
        if (!Objects.equals(this.sname, other.sname)) {
            return false;
        }
        if (this.age != other.age) {
            return false;
        }
        if (!Objects.equals(this.opd, other.opd)) {
            return false;
        }
        if (!Objects.equals(this.gender, other.gender)) {
            return false;
        }
        if (!Objects.equals(this.mstatus, other.mstatus)) {
            return false;
        }
        if (!Objects.equals(this.pdate, other.pdate)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.mno, other.mno)) {
            return false;
        }
        if (!Objects.equals(this.docid, other.docid)) {
            return false;
        }
        if (this.refs != other.refs) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "PatientPojo{" + "pid=" + pid + ", fname=" + fname + ", sname=" + sname + ", age=" + age + ", opd=" + opd + ", gender=" + gender + ", mstatus=" + mstatus + ", pdate=" + pdate + ", address=" + address + ", city=" + city + ", mno=" + mno + ", docid=" + docid + '}';
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOpd() {
        return opd;
    }

    public void setOpd(String opd) {
        this.opd = opd;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMstatus() {
        return mstatus;
    }

    public void setMstatus(String mstatus) {
        this.mstatus = mstatus;
    }

    public Date getPdate() {
        return pdate;
    }

    public void setPdate(Date pdate) {
        this.pdate = pdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }
    private String pid;
    private String fname;
    private String sname;
    private int age;
    private String opd;
    private String gender;
    private String mstatus;
    private Date pdate;

    public int getRefs() {
        return refs;
    }

    public void setRefs(int refs) {
        this.refs = refs;
    }
    private String address;
    private String city;
    private String mno;
    private String docid;
    private int refs;
    
}
