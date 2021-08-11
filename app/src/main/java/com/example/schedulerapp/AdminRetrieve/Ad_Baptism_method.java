package com.example.schedulerapp.AdminRetrieve;

public class Ad_Baptism_method {
    private String  Baptismcelebrant;
    private String  Baptismdate;
    private String Baptismgroup;
    private String Baptismintention;
    private String  Baptismstarttime;
    private String   Baptismvenue;
    private String   BaptismId;

    public Ad_Baptism_method() {
    }

    public Ad_Baptism_method(String baptismcelebrant, String baptismdate, String baptismgroup, String baptismintention, String baptismstarttime, String baptismvenue, String baptismId) {
        Baptismcelebrant = baptismcelebrant;
        Baptismdate = baptismdate;
        Baptismgroup = baptismgroup;
        Baptismintention = baptismintention;
        Baptismstarttime = baptismstarttime;
        Baptismvenue = baptismvenue;
        BaptismId = baptismId;
    }

    public String getBaptismcelebrant() {
        return Baptismcelebrant;
    }

    public void setBaptismcelebrant(String baptismcelebrant) {
        Baptismcelebrant = baptismcelebrant;
    }

    public String getBaptismdate() {
        return Baptismdate;
    }

    public void setBaptismdate(String baptismdate) {
        Baptismdate = baptismdate;
    }

    public String getBaptismgroup() {
        return Baptismgroup;
    }

    public void setBaptismgroup(String baptismgroup) {
        Baptismgroup = baptismgroup;
    }

    public String getBaptismintention() {
        return Baptismintention;
    }

    public void setBaptismintention(String baptismintention) {
        Baptismintention = baptismintention;
    }

    public String getBaptismstarttime() {
        return Baptismstarttime;
    }

    public void setBaptismstarttime(String baptismstarttime) {
        Baptismstarttime = baptismstarttime;
    }

    public String getBaptismvenue() {
        return Baptismvenue;
    }

    public void setBaptismvenue(String baptismvenue) {
        Baptismvenue = baptismvenue;
    }

    public String getBaptismId() {
        return BaptismId;
    }

    public void setBaptismId(String baptismId) {
        BaptismId = baptismId;
    }
}
