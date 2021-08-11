package com.example.schedulerapp.AdminRetrieve;

public class Ad_Adoration_method {
    private String  Adorationdate;
    private String   Adorationintention;
    private String Adorationstarttime;
    private String  Adorationcelebrant;
    private String   Adorationvenue;
    private String   AdorationId;

    public Ad_Adoration_method() {
    }

    public Ad_Adoration_method(String adorationdate, String adorationintention, String adorationstarttime, String adorationcelebrant, String adorationvenue, String adorationId) {
        Adorationdate = adorationdate;
        Adorationintention = adorationintention;
        Adorationstarttime = adorationstarttime;
        Adorationcelebrant = adorationcelebrant;
        Adorationvenue = adorationvenue;
        AdorationId = adorationId;
    }

    public String getAdorationdate() {
        return Adorationdate;
    }

    public void setAdorationdate(String adorationdate) {
        Adorationdate = adorationdate;
    }

    public String getAdorationintention() {
        return Adorationintention;
    }

    public void setAdorationintention(String adorationintention) {
        Adorationintention = adorationintention;
    }

    public String getAdorationstarttime() {
        return Adorationstarttime;
    }

    public void setAdorationstarttime(String adorationstarttime) {
        Adorationstarttime = adorationstarttime;
    }

    public String getAdorationcelebrant() {
        return Adorationcelebrant;
    }

    public void setAdorationcelebrant(String adorationcelebrant) {
        Adorationcelebrant = adorationcelebrant;
    }

    public String getAdorationvenue() {
        return Adorationvenue;
    }

    public void setAdorationvenue(String adorationvenue) {
        Adorationvenue = adorationvenue;
    }

    public String getAdorationId() {
        return AdorationId;
    }

    public void setAdorationId(String adorationId) {
        AdorationId = adorationId;
    }
}
