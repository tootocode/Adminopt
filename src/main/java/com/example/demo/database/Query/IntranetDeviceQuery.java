package com.example.demo.database.Query;
import com.example.demo.entity.devicevulinfo;
import com.example.demo.entity.intranetdeviceInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class IntranetDeviceQuery extends AbstractQuery{
        public IntranetDeviceQuery(Connection connection) throws SQLException{
            super(connection);
    }

    public List<intranetdeviceInfo> queryRecord(String sql){
        List<intranetdeviceInfo> list=new ArrayList<>();

        synchronized (this.connection){
            try{
                ResultSet resultSet = connection.createStatement().executeQuery(sql);
                while(resultSet.next()){
                    intranetdeviceInfo intranetdeviceInfo=new intranetdeviceInfo();
                    intranetdeviceInfo.setIntranetname(resultSet.getString("intranetname"));
                    intranetdeviceInfo.setIP(resultSet.getString("IP"));
                    intranetdeviceInfo.setDevicetype(resultSet.getString("devicetype"));
                    intranetdeviceInfo.setDevice(resultSet.getString("device"));
                    intranetdeviceInfo.setVendor(resultSet.getString("vendor"));
                    intranetdeviceInfo.setDate(resultSet.getString("date"));
                    //System.out.println(deviceInfo.getDevice()+deviceInfo.getDevicetype()+deviceInfo.getVendor());
                    list.add(intranetdeviceInfo);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return list;
    }
}
