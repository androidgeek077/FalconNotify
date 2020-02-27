package Models;

public class StationModel {
    private String name;
    private String registrationno;
    private String address;
    private String userid;
    private String stationemail;
    private String usertype;

    public StationModel() {
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public StationModel(String name, String registrationno, String address, String userid, String stationemail, String usertype, String stationpassword, String helpline) {
        this.name = name;
        this.registrationno = registrationno;
        this.address = address;
        this.userid = userid;
        this.stationemail = stationemail;
        this.usertype = usertype;
        this.stationpassword = stationpassword;
        this.helpline = helpline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationno() {
        return registrationno;
    }

    public void setRegistrationno(String registrationno) {
        this.registrationno = registrationno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStationemail() {
        return stationemail;
    }

    public void setStationemail(String stationemail) {
        this.stationemail = stationemail;
    }

    public String getStationpassword() {
        return stationpassword;
    }

    public void setStationpassword(String stationpassword) {
        this.stationpassword = stationpassword;
    }

    public StationModel(String name, String registrationno, String address, String userid, String stationemail, String stationpassword) {
        this.name = name;
        this.registrationno = registrationno;
        this.address = address;
        this.userid = userid;
        this.stationemail = stationemail;
        this.stationpassword = stationpassword;
    }

    String stationpassword;

    public String getHelpline() {
        return helpline;
    }

    public void setHelpline(String helpline) {
        this.helpline = helpline;
    }

    private String helpline;

    public StationModel(String name, String registrationno, String address, String userid, String stationemail, String stationpassword, String helpline) {
        this.name = name;
        this.registrationno = registrationno;
        this.address = address;
        this.userid = userid;
        this.stationemail = stationemail;
        this.stationpassword = stationpassword;
        this.helpline = helpline;
    }
}
