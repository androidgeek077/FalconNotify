package Models;

public class ReportModel {
    String reportid;
    String reportstatus;

    public String getComplaintid() {
        return complaintid;
    }

    public void setComplaintid(String complaintid) {
        this.complaintid = complaintid;
    }

    String complaintid;

    public String getReportstatus() {
        return reportstatus;
    }

    public void setReportstatus(String reportstatus) {
        this.reportstatus = reportstatus;
    }

    public ReportModel(String complaintid,String reportid, String reportstatus, String headline, String imageurl, String reportdetail, String policeStation) {
        this.reportid = reportid;
        this. complaintid = complaintid;
        this.reportstatus = reportstatus;
        this.headline = headline;
        this.imageurl = imageurl;
        this.reportdetail = reportdetail;
        PoliceStation = policeStation;
    }

    public ReportModel() {
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getReportdetail() {
        return reportdetail;
    }

    public void setReportdetail(String reportdetail) {
        this.reportdetail = reportdetail;
    }



    String headline;
    String imageurl;
    String reportdetail;

    public String getPoliceStation() {
        return PoliceStation;
    }

    public void setPoliceStation(String policeStation) {
        PoliceStation = policeStation;
    }



    String PoliceStation;
}
