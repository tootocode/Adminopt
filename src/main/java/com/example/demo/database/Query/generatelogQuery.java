package com.example.demo.database.Query;

import com.example.demo.entity.ipdevicetypeInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class generatelogQuery extends AbstractQuery{
    public generatelogQuery(Connection connection) throws SQLException {
        super(connection);
    }

    public List<ipdevicetypeInfo> queryRecord(String sql) throws SQLException {
        List<ipdevicetypeInfo> list=new ArrayList<>();
        synchronized (this.connection){
            ResultSet resultSet=connection.createStatement().executeQuery(sql);
            int i=1;
            while (resultSet.next()){
                ipdevicetypeInfo ipdevicetypeInfo=new ipdevicetypeInfo();
                ipdevicetypeInfo.setIP(resultSet.getString("IP"));
                ipdevicetypeInfo. setDevicetype(resultSet.getString("DeviceType"));
                ipdevicetypeInfo. setVendor(resultSet.getString("Vendor"));

                list.add(ipdevicetypeInfo);
                i++;
            }
        }
        return list;
    }

}
