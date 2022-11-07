package com.example.demo.database.Query;

import com.example.demo.entity.listInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibQuery extends AbstractQuery{
    public LibQuery(Connection connection) throws SQLException {
        super(connection);
    }
    public String queryRecord(String host) throws SQLException {
        String lib="";
        String ip=host.split(":")[0];
        String sql="select cryLib from CryLibTable where host='"+ip+"'";
        synchronized (this.connection){
            ResultSet resultSet=connection.createStatement().executeQuery(sql);
            if(resultSet.next()){
                lib=resultSet.getString("cryLib");
            }
        }
        return lib;
    }
}
