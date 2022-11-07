package com.example.demo.database.Record;
import java.sql.*;
public class IntranetDeviceRecord extends AbstractRecord{
    IntranetDeviceRecord(Connection connection) throws SQLException{
        super(connection);
    }
    public void addRecord(String intranetname, String ip, String device, String vendor,String devicetype, String date)  {
        String table="IntranetDeviceTable";
        synchronized (connection){
            String sql="SELECT table_name FROM information_schema.TABLES WHERE table_name='"+table+"';";
            Statement statement= null;
            try{
                statement = this.connection.createStatement();
                ResultSet set=statement.executeQuery(sql);
                if(!set.next()){
                    this.connection.createStatement().executeUpdate("CREATE TABLE if NOT EXISTS "+table+" (intranetname text,IP text,device text,vendor text,devicetype text, date text);");
                }
                sql="SELECT * from "+table+" where IP='"+ip+"' and intranetname='" + intranetname + "';";
                ResultSet resultSet=connection.createStatement().executeQuery(sql);
                if(!resultSet.next()){
                    PreparedStatement ptmt=connection.prepareStatement("insert into "+table+"(intranetname ,IP ,device ,vendor ,devicetype, date ) values (?,?,?,?,?,?)");
                    ptmt.setString(1,intranetname);
                    ptmt.setString(2,ip);
                    ptmt.setString(3,device);
                    ptmt.setString(4,vendor);
                    ptmt.setString(5,devicetype);
                    ptmt.setString(6,date);
                    ptmt.executeUpdate();
                    ptmt.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
