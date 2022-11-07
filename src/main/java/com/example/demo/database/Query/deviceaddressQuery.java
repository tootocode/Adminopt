package com.example.demo.database.Query;

import com.example.demo.entity.deviceaddressinfo;
import com.example.demo.entity.devicetaskInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class deviceaddressQuery extends AbstractQuery{
    public deviceaddressQuery(Connection connection) throws SQLException {
        super(connection);
    }
    public List<deviceaddressinfo> queryRecord(String sql){
        List<deviceaddressinfo> list=new ArrayList<>();

        synchronized (this.connection){
            try{
                ResultSet resultSet = connection.createStatement().executeQuery(sql);
                while(resultSet.next()){
                    deviceaddressinfo devicetaskInfo=new deviceaddressinfo();

                    devicetaskInfo.setTarget(resultSet.getString("IP"));
                    devicetaskInfo.setCountry(resultSet.getString("country"));
                    devicetaskInfo.setProvince(resultSet.getString("province"));
                    devicetaskInfo.setCity(resultSet.getString("city"));

                    list.add(devicetaskInfo);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return list;
    }
}