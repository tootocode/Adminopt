package com.example.demo.database.Query;

import com.example.demo.entity.ALLVulInfo;
import com.example.demo.entity.ipdevicetypeInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ALLVulQuery extends AbstractQuery {
    public ALLVulQuery(Connection connection) throws SQLException {
        super(connection);
    }

    public ALLVulInfo queryRecord(String sql) throws SQLException {
        //List<ALLVulInfo> list=new ArrayList<>();
        ALLVulInfo ALLVulInfo = new ALLVulInfo();
        synchronized (this.connection) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            if (resultSet.next()) {
                ALLVulInfo.setHeartbleed(resultSet.getInt("heartbleed"));
                ALLVulInfo.setCss(resultSet.getInt("css"));
                ALLVulInfo.setTicketbleed(resultSet.getInt("ticketbleed"));
                ALLVulInfo.setRobot(resultSet.getInt("robot"));
                ALLVulInfo.setCrime(resultSet.getInt("crime"));
                ALLVulInfo.setBreach(resultSet.getInt("breach"));
                ALLVulInfo.setSweet32(resultSet.getInt("sweet32"));
                ALLVulInfo.setFreak(resultSet.getInt("freak"));
                ALLVulInfo.setLogjam(resultSet.getInt("logjam"));
                ALLVulInfo.setBeast(resultSet.getInt("beast"));
                ALLVulInfo.setLucky13(resultSet.getInt("lucky13"));
                ALLVulInfo.setRc4(resultSet.getInt("rc4"));
                ALLVulInfo.setPoodle(resultSet.getInt("poodle"));
            }
            //list.add(ALLVulInfo);
        }
        return ALLVulInfo;
    }
}
