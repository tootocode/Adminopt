package com.example.demo.database.Query;

import com.example.demo.entity.InfoEntity;
import com.example.demo.entity.devicevulinfo;
import com.example.demo.entity.searchvulInfo;
import com.example.demo.entity.vulnerability;
import sun.awt.X11.XSystemTrayPeer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class searchvulQuery extends AbstractQuery{
    public searchvulQuery(Connection connection) throws SQLException {
        super(connection);
    }

    public List<searchvulInfo> queryRecord(String sql){
        List<searchvulInfo> list=new ArrayList<>();

        synchronized (this.connection){
            try{
                ResultSet resultSet = connection.createStatement().executeQuery(sql);
                while(resultSet.next()){
                    searchvulInfo devicevulinfo=new searchvulInfo();
                    devicevulinfo.setTitle(resultSet.getString("title"));
                    devicevulinfo.setDevicetype(resultSet.getString("device"));
                    devicevulinfo.setVendors(resultSet.getString("vendor"));
                    devicevulinfo.setTaskname(resultSet.getString("taskname"));
                    devicevulinfo.setIp(resultSet.getString("IP"));

                    //System.out.println(deviceInfo.getDevice()+deviceInfo.getDevicetype()+deviceInfo.getVendor());
                    list.add(devicevulinfo);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return list;
    }
}
