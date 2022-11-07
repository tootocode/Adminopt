package com.example.demo.entity;

public class ipdevicetypeInfo extends InfoEntity{
    private String ip;
    private String vendor;
    private String devicetype;

    public void setIP(String ip) {
        this.ip = ip;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getIP() {
        return ip;
    }
    public String getVendor() {
        return vendor;
    }
    public String getDevicetype() {
        return devicetype;
    }
}
