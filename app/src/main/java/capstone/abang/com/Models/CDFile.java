package capstone.abang.com.Models;

/**
 * Created by Pc-user on 21/01/2018.
 */

public class CDFile {
    private String CDCode;
    private String CDModel;
    private long cdcaryear;
    private String CDMaker;
    private String cdchassisno;
    private String CDPhoto;
    private int CDCapacity;
    private String CDTransmission;
    private String cdplateno;
    private String cdengineno;
    private String cdcatcode;
    private String cdowner;
    private String cdstatus;
    private long cdrentrate;

    public CDFile() {

    }

    public CDFile(String CDCode, String CDModel, long cdcaryear, String CDMaker, String cdchassisno, String CDPhoto, int CDCapacity, String CDTransmission, String cdplateno, String cdengineno, String cdcatcode, String cdowner, String cdstatus, long cdrentrate) {
        this.CDCode = CDCode;
        this.CDModel = CDModel;
        this.cdcaryear = cdcaryear;
        this.CDMaker = CDMaker;
        this.cdchassisno = cdchassisno;
        this.CDPhoto = CDPhoto;
        this.CDCapacity = CDCapacity;
        this.CDTransmission = CDTransmission;
        this.cdplateno = cdplateno;
        this.cdengineno = cdengineno;
        this.cdcatcode = cdcatcode;
        this.cdowner = cdowner;
        this.cdstatus = cdstatus;
        this.cdrentrate = cdrentrate;
    }

    public String getCDCode() {
        return CDCode;
    }

    public void setCDCode(String CDCode) {
        this.CDCode = CDCode;
    }

    public String getCDModel() {
        return CDModel;
    }

    public void setCDModel(String CDModel) {
        this.CDModel = CDModel;
    }

    public long getCdcaryear() {
        return cdcaryear;
    }

    public void setCdcaryear(long cdcaryear) {
        this.cdcaryear = cdcaryear;
    }

    public String getCDMaker() {
        return CDMaker;
    }

    public void setCDMaker(String CDMaker) {
        this.CDMaker = CDMaker;
    }

    public String getCdchassisno() {
        return cdchassisno;
    }

    public void setCdchassisno(String cdchassisno) {
        this.cdchassisno = cdchassisno;
    }

    public String getCDPhoto() {
        return CDPhoto;
    }

    public void setCDPhoto(String CDPhoto) {
        this.CDPhoto = CDPhoto;
    }

    public int getCDCapacity() {
        return CDCapacity;
    }

    public void setCDCapacity(int CDCapacity) {
        this.CDCapacity = CDCapacity;
    }

    public String getCDTransmission() {
        return CDTransmission;
    }

    public void setCDTransmission(String CDTransmission) {
        this.CDTransmission = CDTransmission;
    }

    public String getCdplateno() {
        return cdplateno;
    }

    public void setCdplateno(String cdplateno) {
        this.cdplateno = cdplateno;
    }

    public String getCdengineno() {
        return cdengineno;
    }

    public void setCdengineno(String cdengineno) {
        this.cdengineno = cdengineno;
    }

    public String getCdcatcode() {
        return cdcatcode;
    }

    public void setCdcatcode(String cdcatcode) {
        this.cdcatcode = cdcatcode;
    }

    public String getCdowner() {
        return cdowner;
    }

    public void setCdowner(String cdowner) {
        this.cdowner = cdowner;
    }

    public String getCdstatus() {
        return cdstatus;
    }

    public void setCdstatus(String cdstatus) {
        this.cdstatus = cdstatus;
    }

    public long getCdrentrate() {
        return cdrentrate;
    }

    public void setCdrentrate(long cdrentrate) {
        this.cdrentrate = cdrentrate;
    }
}
