package com.example.schedulerapp.Adapters;

public class Adoration_method {
    private String Adorationcelebrant;
    private String Adorationdate;
    private String Adorationendtime;
    private String Adorationstarttime;
    private String Adorationvenue;

    public Adoration_method() {

    }

    public Adoration_method(String adorationcelebrant, String adorationdate, String adorationendtime, String adorationstarttime, String adorationvenue) {
        Adorationcelebrant = adorationcelebrant;
        Adorationdate = adorationdate;
        Adorationendtime = adorationendtime;
        Adorationstarttime = adorationstarttime;
        Adorationvenue = adorationvenue;
    }

    public String getAdorationcelebrant() {
        return Adorationcelebrant;
    }

    public void setAdorationcelebrant(String adorationcelebrant) {
        Adorationcelebrant = adorationcelebrant;
    }

    public String getAdorationdate() {
        return Adorationdate;
    }

    public void setAdorationdate(String adorationdate) {
        Adorationdate = adorationdate;
    }

    public String getAdorationendtime() {
        return Adorationendtime;
    }

    public void setAdorationendtime(String adorationendtime) {
        Adorationendtime = adorationendtime;
    }

    public String getAdorationstarttime() {
        return Adorationstarttime;
    }

    public void setAdorationstarttime(String adorationstarttime) {
        Adorationstarttime = adorationstarttime;
    }

    public String getAdorationvenue() {
        return Adorationvenue;
    }

    public void setAdorationvenue(String adorationvenue) {
        Adorationvenue = adorationvenue;
    }
}
