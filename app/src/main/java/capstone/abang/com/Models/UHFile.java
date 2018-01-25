package capstone.abang.com.Models;

/**
 * Created by Pc-user on 09/01/2018.
 */

public class UHFile {
    private String UHUsercode;
    private String UHUsername;
    private String UHPassword;
    private String UHStatus;
    private String uhdatecreated;

    public String getUHDateCreated() {
        return uhdatecreated;
    }

    public void setUHDateCreated(String UHDateCreated) {
        this.uhdatecreated = UHDateCreated;
    }

    public UHFile() {

    }

    public UHFile(String UHUsercode, String UHUsername, String UHPassword, String UHStatus, String uhdatecreated) {
        this.UHUsercode = UHUsercode;
        this.UHUsername = UHUsername;
        this.UHPassword = UHPassword;
        this.UHStatus = UHStatus;
        this.uhdatecreated = uhdatecreated;
    }

    public String getUHUsercode() {
        return UHUsercode;
    }

    public String getUHUsername() {
        return UHUsername;
    }

    public String getUHPassword() {
        return UHPassword;
    }

    public String getUHStatus() {
        return UHStatus;
    }

    public void setUHUsercode(String UHUsercode) {
        this.UHUsercode = UHUsercode;
    }

    public void setUHUsername(String UHUsername) {
        this.UHUsername = UHUsername;
    }

    public void setUHPassword(String UHPassword) {
        this.UHPassword = UHPassword;
    }

    public void setUHStatus(String UHStatus) {
        this.UHStatus = UHStatus;
    }
}
