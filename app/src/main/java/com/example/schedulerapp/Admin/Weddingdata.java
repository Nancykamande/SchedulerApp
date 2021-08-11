package com.example.schedulerapp.Admin;

public class Weddingdata {
    private String BRIDENAME;
    private String GROOMNAME;
    private String DATE;
    private String VENUE;
    private String CELEBRANT;

    public Weddingdata() {
    }

    public Weddingdata(String BRIDENAME, String GROOMNAME, String DATE, String VENUE, String CELEBRANT) {
        this.BRIDENAME = BRIDENAME;
        this.GROOMNAME = GROOMNAME;
        this.DATE = DATE;
        this.VENUE = VENUE;
        this.CELEBRANT = CELEBRANT;
    }

    public String getBRIDENAME() {
        return BRIDENAME;
    }

    public void setBRIDENAME(String BRIDENAME) {
        this.BRIDENAME = BRIDENAME;
    }

    public String getGROOMNAME() {
        return GROOMNAME;
    }

    public void setGROOMNAME(String GROOMNAME) {
        this.GROOMNAME = GROOMNAME;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getVENUE() {
        return VENUE;
    }

    public void setVENUE(String VENUE) {
        this.VENUE = VENUE;
    }

    public String getCELEBRANT() {
        return CELEBRANT;
    }

    public void setCELEBRANT(String CELEBRANT) {
        this.CELEBRANT = CELEBRANT;
    }
}
