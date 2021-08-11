package com.example.schedulerapp.Adapters;

public class popfuneral_method {
    private String FAge;
    private String  FCard;
    private String   FEmail;
    private String  FName;
    private String  FRelative;
    private String  FVenue;
    private String   Mcontact;

    public popfuneral_method() {
    }

    public popfuneral_method(String FAge, String FCard, String FEmail, String FName, String FRelative, String FVenue, String mcontact) {
        this.FAge = FAge;
        this.FCard = FCard;
        this.FEmail = FEmail;
        this.FName = FName;
        this.FRelative = FRelative;
        this.FVenue = FVenue;
        Mcontact = mcontact;
    }

    public String getFAge() {
        return FAge;
    }

    public void setFAge(String FAge) {
        this.FAge = FAge;
    }

    public String getFCard() {
        return FCard;
    }

    public void setFCard(String FCard) {
        this.FCard = FCard;
    }

    public String getFEmail() {
        return FEmail;
    }

    public void setFEmail(String FEmail) {
        this.FEmail = FEmail;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getFRelative() {
        return FRelative;
    }

    public void setFRelative(String FRelative) {
        this.FRelative = FRelative;
    }

    public String getFVenue() {
        return FVenue;
    }

    public void setFVenue(String FVenue) {
        this.FVenue = FVenue;
    }

    public String getMcontact() {
        return Mcontact;
    }

    public void setMcontact(String mcontact) {
        Mcontact = mcontact;
    }
}
