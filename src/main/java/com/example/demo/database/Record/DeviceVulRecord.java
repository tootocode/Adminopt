package com.example.demo.database.Record;

import java.sql.*;

public class DeviceVulRecord extends AbstractRecord{
    DeviceVulRecord(Connection connection) throws SQLException {
        super(connection);
    }

    public void addRecord(String taskname, String ip, String device, String vendor,String taskdate, String title, String hanlvl, String description)  {
        String table="DeviceVulTable";
        synchronized (connection){
            String sql="SELECT table_name FROM information_schema.TABLES WHERE table_name='"+table+"';";
            Statement statement= null;
            try{
                statement = this.connection.createStatement();
                ResultSet set=statement.executeQuery(sql);
                if(!set.next()){
                    this.connection.createStatement().executeUpdate("CREATE TABLE if NOT EXISTS "+table+" (taskname text,IP text,device text,vendor text,taskdate text, title text,hanlvl text,description text);");
                }
                sql="SELECT * from "+table+" where IP='"+ip+"';";
                ResultSet resultSet=connection.createStatement().executeQuery(sql);
                if(!resultSet.next()){
                    PreparedStatement ptmt=connection.prepareStatement("insert into "+table+"(taskname ,IP ,device ,vendor ,taskdate, title ,hanlvl ,description) values (?,?,?,?,?,?,?,?)");
                    ptmt.setString(1,taskname);
                    ptmt.setString(2,ip);
                    ptmt.setString(3,device);
                    ptmt.setString(4,vendor);
                    ptmt.setString(5,taskdate);
                    ptmt.setString(6,title);
                    ptmt.setString(7,hanlvl);
                    ptmt.setString(8,description);
                    ptmt.executeUpdate();
                    ptmt.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
