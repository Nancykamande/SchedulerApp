package com.example.schedulerapp.Adapters;

public class Baptism_method {
    private String Baptismcelebrant;
    private String Baptismdate;
    private String Baptismendtime;
    private String Baptismgroup;
    private String Baptismstarttime;
    private String Baptismvenue;
    private String BaptismId;

    public Baptism_method() {
    }

    public Baptism_method(String baptismcelebrant, String baptismdate, String baptismendtime, String baptismgroup, String baptismstarttime, String baptismvenue, String baptismId) {
        Baptismcelebrant = baptismcelebrant;
        Baptismdate = baptismdate;
        Baptismendtime = baptismendtime;
        Baptismgroup = baptismgroup;
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

    public String getBaptismendtime() {
        return Baptismendtime;
    }

    public void setBaptismendtime(String baptismendtime) {
        Baptismendtime = baptismendtime;
    }

    public String getBaptismgroup() {
        return Baptismgroup;
    }

    public void setBaptismgroup(String baptismgroup) {
        Baptismgroup = baptismgroup;
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

