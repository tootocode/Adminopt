package com.example.demo.entity;

public class intranetdeviceInfo {
    private String intranetname;
    private String IP;
    private String device;
    private String vendor;
    private String devicetype;
    private String date;

    public void setDevice(String device) {
        this.device = device;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDevice() {
        return device;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public String getVendor() {
        return vendor;
    }

    public void setIP(String IP){this.IP = IP;}

    public String getIP(){return IP;}

    public void setIntranetname(String intranetname){this.intranetname = intranetname; }

    public String getIntranetname(){return intranetname;}

    public void setDate(String date){this.date = date; }

    public String getDate(){return date; }
}
