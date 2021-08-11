package com.example.schedulerapp.AdminRetrieve;

public class Ad_funeral_method {
    private String   DAGE;
    private String   DCELEBRANT;
    private String DNAME;
    private String  DINTENTION;
    private String  DTIME;
    private String DVENUE;
    private String UDATE;
    private String FuneralId;

    public Ad_funeral_method() {
    }

    public Ad_funeral_method(String DAGE, String DCELEBRANT, String DNAME, String DINTENTION, String DTIME, String DVENUE, String UDATE, String funeralId) {
        this.DAGE = DAGE;
        this.DCELEBRANT = DCELEBRANT;
        this.DNAME = DNAME;
        this.DINTENTION = DINTENTION;
        this.DTIME = DTIME;
        this.DVENUE = DVENUE;
        this.UDATE = UDATE;
        FuneralId = funeralId;
    }

    public String getDAGE() {
        return DAGE;
    }

    public void setDAGE(String DAGE) {
        this.DAGE = DAGE;
    }

    public String getDCELEBRANT() {
        return DCELEBRANT;
    }

    public void setDCELEBRANT(String DCELEBRANT) {
        this.DCELEBRANT = DCELEBRANT;
    }

    public String getDNAME() {
        return DNAME;
    }

    public void setDNAME(String DNAME) {
        this.DNAME = DNAME;
    }

    public String getDINTENTION() {
        return DINTENTION;
    }

    public void setDINTENTION(String DINTENTION) {
        this.DINTENTION = DINTENTION;
    }

    public String getDTIME() {
        return DTIME;
    }

    public void setDTIME(String DTIME) {
        this.DTIME = DTIME;
    }

    public String getDVENUE() {
        return DVENUE;
    }

    public void setDVENUE(String DVENUE) {
        this.DVENUE = DVENUE;
    }

    public String getUDATE() {
        return UDATE;
    }

    public void setUDATE(String UDATE) {
        this.UDATE = UDATE;
    }

    public String getFuneralId() {
        return FuneralId;
    }

    public void setFuneralId(String funeralId) {
        FuneralId = funeralId;
    }
}

