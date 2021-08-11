package com.example.schedulerapp;

public class Makechanges_method {
    private String UBridename;
    private String UGroomname;
    private String UDate;
    private String UVenue;
    private String Ucelebrant;

    public Makechanges_method() {

    }

    public Makechanges_method(String UBridename, String UGroomname, String UDate, String UVenue, String ucelebrant) {
        this.UBridename = UBridename;
        this.UGroomname = UGroomname;
        this.UDate = UDate;
        this.UVenue = UVenue;
        Ucelebrant = ucelebrant;
    }

    public String getUBridename() {
        return UBridename;
    }

    public void setUBridename(String UBridename) {
        this.UBridename = UBridename;
    }

    public String getUGroomname() {
        return UGroomname;
    }

    public void setUGroomname(String UGroomname) {
        this.UGroomname = UGroomname;
    }

    public String getUDate() {
        return UDate;
    }

    public void setUDate(String UDate) {
        this.UDate = UDate;
    }

    public String getUVenue() {
        return UVenue;
    }

    public void setUVenue(String UVenue) {
        this.UVenue = UVenue;
    }

    public String getUcelebrant() {
        return Ucelebrant;
    }

    public void setUcelebrant(String ucelebrant) {
        Ucelebrant = ucelebrant;
    }
}
