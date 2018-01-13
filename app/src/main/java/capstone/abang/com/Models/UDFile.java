package capstone.abang.com.Models;

/**
 * Created by Pc-user on 09/01/2018.
 */

public class UDFile {
    private String UDUserCode;
    private String UDFullname;
    private String UDAddr;
    private String UDEmail;
    private String UDStatus;
    private String UDUserType;
    private String UDContact;
    private String UDImageNbi;

    public UDFile() {

    }

    public UDFile(String UDUserCode, String UDFullname, String UDAddr, String UDEmail, String UDStatus, String UDUserType, String UDContact, String UDImageNbi) {
        this.UDUserCode = UDUserCode;
        this.UDFullname = UDFullname;
        this.UDAddr = UDAddr;
        this.UDEmail = UDEmail;
        this.UDStatus = UDStatus;
        this.UDUserType = UDUserType;
        this.UDContact = UDContact;
        this.UDImageNbi = UDImageNbi;
    }

    public String getUDUserCode() {
        return UDUserCode;
    }

    public String getUDFullname() {
        return UDFullname;
    }

    public String getUDAddr() {
        return UDAddr;
    }

    public String getUDEmail() {
        return UDEmail;
    }

    public String getUDStatus() {
        return UDStatus;
    }

    public String getUDUserType() {
        return UDUserType;
    }

    public String getUDContact() {
        return UDContact;
    }

    public String getUDImageNbi() {
        return UDImageNbi;
    }

    public void setUDUserCode(String UDUserCode) {
        this.UDUserCode = UDUserCode;
    }

    public void setUDFullname(String UDFullname) {
        this.UDFullname = UDFullname;
    }

    public void setUDAddr(String UDAddr) {
        this.UDAddr = UDAddr;
    }

    public void setUDEmail(String UDEmail) {
        this.UDEmail = UDEmail;
    }

    public void setUDStatus(String UDStatus) {
        this.UDStatus = UDStatus;
    }

    public void setUDUserType(String UDUserType) {
        this.UDUserType = UDUserType;
    }

    public void setUDContact(String UDContact) {
        this.UDContact = UDContact;
    }

    public void setUDImageNbi(String UDImageNbi) {
        this.UDImageNbi = UDImageNbi;
    }
}
