package com.example.schedulerapp.Adapters;

public class Funeral_method {
    private String DNAME;
    private String DTIME;
    private String UDATE;
    private String DVENUE;
    private String DCELEBRANT;

    public Funeral_method() {
    }

    public Funeral_method(String DNAME, String DTIME, String UDATE, String DVENUE, String DCELEBRANT) {
        this.DNAME = DNAME;
        this.DTIME = DTIME;
        this.UDATE = UDATE;
        this.DVENUE = DVENUE;
        this.DCELEBRANT = DCELEBRANT;
    }

    public String getDNAME() {
        return DNAME;
    }

    public void setDNAME(String DNAME) {
        this.DNAME = DNAME;
    }

    public String getDTIME() {
        return DTIME;
    }

    public void setDTIME(String DTIME) {
        this.DTIME = DTIME;
    }

    public String getUDATE() {
        return UDATE;
    }

    public void setUDATE(String UDATE) {
        this.UDATE = UDATE;
    }

    public String getDVENUE() {
        return DVENUE;
    }

    public void setDVENUE(String DVENUE) {
        this.DVENUE = DVENUE;
    }

    public String getDCELEBRANT() {
        return DCELEBRANT;
    }

    public void setDCELEBRANT(String DCELEBRANT) {
        this.DCELEBRANT = DCELEBRANT;
    }
}
