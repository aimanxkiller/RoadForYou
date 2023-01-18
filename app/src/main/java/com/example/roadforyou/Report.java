package com.example.roadforyou;

public class Report {

    private String reportName,description,userID;
    private Double latitude,longitude;

    public  Report(){}

    public Report(String reportName,String desc,String userid,Double lat,Double longi){
        this.reportName = reportName;
        this.description = desc;
        this.userID = userid;
        this.latitude = lat;
        this.longitude = longi;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
