package com.example.demo.database.Record;

public interface Record {
    void addRecord(String markRes);

    void addRecord(String name, String desc);

    void addRecord(String vendor, String name, String devicetype);

    void addRecord(String name, String target, String bug, String desc);

    void addRecord(String taskname, String ip, String device, String vendor,String taskdate,String title, String hanlvl, String description);

    void addRecord(String intranetname, String ip, String device, String vendor,String devicetype, String date);
}
