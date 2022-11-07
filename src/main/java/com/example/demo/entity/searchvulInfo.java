package com.example.demo.entity;

public class searchvulInfo {

    private String title;
    private String devicetype;
    private String vendors;
    private String taskname;
    private String ip;

    public void setTitle(String vendor) {
        this.title = vendor;
    }
    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }
    public void setVendors(String devicetype) {
        this.vendors = devicetype;
    }
    public void setTaskname(String taskname){this.taskname = taskname; }
    public void setIp(String ip){this.ip = ip; }

    public String getTitle() {
        return title;
    }
    public String getDevicetype() {
        return devicetype;
    }
    public String getVendors() {
        return vendors;
    }
    public String getTaskname(){return taskname; }
    public String getIp(){return ip; }

}
