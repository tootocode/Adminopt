package com.example.demo.entity;


public class devicevulinfo {

    private String hanlvl;
    private String title;
    private String description;
    private String devicetype;
    private String vendors;
    private String taskname;
    private String ip;
    private String taskdate;

    public void setHanlvl(String vendor) {
        this.hanlvl = vendor;
    }
    public void setTitle(String vendor) {
        this.title = vendor;
    }
    public void setDescription(String vendor) {
        this.description = vendor;
    }
    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }
    public void setVendors(String devicetype) {
        this.vendors = devicetype;
    }
    public void setTaskname(String taskname){this.taskname = taskname; }
    public void setIp(String ip){this.ip = ip; }
    public void setTaskdate(String taskdate){this.taskdate = taskdate; }

    public String getHanlvl() {
        return hanlvl;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getDevicetype() {
        return devicetype;
    }
    public String getVendors() {
        return vendors;
    }
    public String getTaskname(){return taskname; }
    public String getIp(){return ip; }
    public String getTaskdate(){return taskdate; }

}

