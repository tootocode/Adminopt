package com.example.demo.database.Query;

import com.example.demo.entity.InfoEntity;
import com.example.demo.entity.devicevulinfo;
import com.example.demo.entity.vulnerability;
import sun.awt.X11.XSystemTrayPeer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class devicevulQuery extends AbstractQuery{
    public devicevulQuery(Connection connection) throws SQLException {
        super(connection);
    }

    public List<devicevulinfo> queryRecord(String sql){
        List<devicevulinfo> list=new ArrayList<>();

        synchronized (this.connection){
            try{
                ResultSet resultSet = connection.createStatement().executeQuery(sql);
                while(resultSet.next()){
                    devicevulinfo devicevulinfo=new devicevulinfo();
                    devicevulinfo.setTitle(resultSet.getString("title"));
                    devicevulinfo.setHanlvl(resultSet.getString("hanlvl"));
                    devicevulinfo.setDevicetype(resultSet.getString("device"));
                    devicevulinfo.setDescription(resultSet.getString("description"));
                    devicevulinfo.setVendors(resultSet.getString("vendor"));
                    devicevulinfo.setTaskname(resultSet.getString("taskname"));
                    devicevulinfo.setIp(resultSet.getString("IP"));
                    devicevulinfo.setTaskdate(resultSet.getString("taskdate"));

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
