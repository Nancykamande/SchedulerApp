package com.example.schedulerapp;

public class Report {
    private String testcelebrant;
    private String testdate;
    private String testendtime;
    private String testvenue;

    public Report() {
    }

    public Report(String testcelebrant, String testdate, String testendtime, String testvenue) {
        this.testcelebrant = testcelebrant;
        this.testdate = testdate;
        this.testendtime = testendtime;
        this.testvenue = testvenue;
    }

    public String getTestcelebrant() {
        return testcelebrant;
    }

    public void setTestcelebrant(String testcelebrant) {
        this.testcelebrant = testcelebrant;
    }

    public String getTestdate() {
        return testdate;
    }

    public void setTestdate(String testdate) {
        this.testdate = testdate;
    }

    public String getTestendtime() {
        return testendtime;
    }

    public void setTestendtime(String testendtime) {
        this.testendtime = testendtime;
    }

    public String getTestvenue() {
        return testvenue;
    }

    public void setTestvenue(String testvenue) {
        this.testvenue = testvenue;
    }
}
