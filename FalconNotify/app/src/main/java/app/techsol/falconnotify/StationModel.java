package app.techsol.falconnotify;

public class StationModel {
    String name;
    String registrationno;
    String address;

    public StationModel() {
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

    public String getHelplineno() {
        return helplineno;
    }

    public void setHelplineno(String helplineno) {
        this.helplineno = helplineno;
    }

    String helplineno;

    public StationModel(String name, String registrationno, String address, String helplineno) {
        this.name = name;
        this.registrationno = registrationno;
        this.address = address;
        this.helplineno = helplineno;
    }
}
