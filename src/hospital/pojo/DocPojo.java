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
public class DocPojo {

    @Override
    public String toString() {
        return "DocPojo{" + "docid=" + docid + ", userid=" + userid + ", qualification=" + qualification + ", specility=" + specility + '}';
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecility() {
        return specility;
    }

    public void setSpecility(String specility) {
        this.specility = specility;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    private String userid;
    private String docid;
    private String qualification;
    private String specility;
    private String status;
    //Corresponds to table Doctors
    
}
