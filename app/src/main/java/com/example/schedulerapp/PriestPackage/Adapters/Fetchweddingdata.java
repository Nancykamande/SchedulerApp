package com.example.schedulerapp.Adapters;

public class Fetchweddingdata {

    private String UBridename;
    private String UGroomname;
    private String UDate;
    private String UVenue;
    private String Ucelebrant;
    private String UIntention;
    private String UStarttime;

    public Fetchweddingdata() {
    }

    public Fetchweddingdata(String UBridename, String UGroomname, String UDate, String UVenue, String ucelebrant, String UIntention, String UStarttime) {
        this.UBridename = UBridename;
        this.UGroomname = UGroomname;
        this.UDate = UDate;
        this.UVenue = UVenue;
        Ucelebrant = ucelebrant;
        this.UIntention = UIntention;
        this.UStarttime = UStarttime;
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

    public String getUIntention() {
        return UIntention;
    }

    public void setUIntention(String UIntention) {
        this.UIntention = UIntention;
    }

    public String getUStarttime() {
        return UStarttime;
    }

    public void setUStarttime(String UStarttime) {
        this.UStarttime = UStarttime;
    }
}
