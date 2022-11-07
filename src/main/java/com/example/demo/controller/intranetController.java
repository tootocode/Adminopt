package com.example.demo.controller;
import com.example.demo.database.Query.IntranetDeviceQuery;
import com.example.demo.database.Query.deviceInfoQuery;
import com.example.demo.database.Query.devicemarktaskQuery;
import com.example.demo.database.Query.searchvulQuery;
import com.example.demo.database.Record.*;
import com.example.demo.entity.deviceInfo;
import com.example.demo.entity.devicetaskInfo;
import com.example.demo.entity.intranetdeviceInfo;
import com.example.demo.entity.searchvulInfo;
import com.example.demo.queue.ActivemqConfig;
import com.example.demo.redis.RedisConfig;
import com.example.demo.redis.RedisLock;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.net.Socket;

@RestController
@CrossOrigin
public class intranetController {
    @Autowired
    private DatabaseConfig databaseConfig;
    private Socket socket;

    @Autowired(required = false)
    private ActivemqConfig activemqConfig;
    @Autowired
    private RedisConfig redisConfig;

    @GetMapping("/getintranetinfolist")
    public List<devicetaskInfo> getInitList(){
        List<devicetaskInfo> list=new ArrayList<>();
        return list;
    }

    @GetMapping("/connectserver")
    public String connectserver(String IP, int port){
        System.out.println(IP);
        System.out.println(port);
        try {
            Socket socket = new Socket(IP,port);
            this.socket = socket;
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "链接失败";
        }
    }

    @GetMapping("/postipinfo")
    public String postipinfo(String intranetname, String ip, String date){
        try{
            System.out.println("将要扫描的地址发送给服务器！");
            OutputStream out = this.socket.getOutputStream();
            PrintWriter pw=new PrintWriter(out);//将输出流包装为打印流
//            out.write(("submit*ipaddr*"+intranetname+"*" + ip));
            pw.write("submit*ipaddr*"+intranetname+"*" + ip);
            pw.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "success";
    }

    @GetMapping("/postipinfo_upnp")
    public String postipinfo_upnp(String intranetname, String ip, String date){
//        try{
//            System.out.println("将要扫描的地址发送给服务器！");
//            OutputStream out = this.socket.getOutputStream();
//            PrintWriter pw=new PrintWriter(out);//将输出流包装为打印流
////            out.write(("submit*ipaddr*"+intranetname+"*" + ip));
//            pw.write("submit*ipaddr*"+intranetname+"*" + ip);
//            pw.flush();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        System.out.println(intranetname + ":"+ip);

        return "success";
    }

    @GetMapping("/update/database")
    public String updatedatabase(){
        try{
            System.out.println("将要扫描的地址发送给服务器！");
            OutputStream out = this.socket.getOutputStream();
            PrintWriter pw=new PrintWriter(out);//将输出流包装为打印流
//            out.write(("submit*ipaddr*"+intranetname+"*" + ip));
            pw.write("to*send*vue");
            pw.flush();
//            this.socket.shutdownOutput();
//            Thread.sleep(5000);
            InputStream is=this.socket.getInputStream();
            InputStreamReader ipsr = new InputStreamReader(is, "utf-8");
            BufferedReader in = new BufferedReader(ipsr );
            String info=null;
            while((info=in.readLine())!=null){
                System.out.println("Python服务器说："+info);
//                socket.close();
                break;
            }
//            Record record = new IntranetDeviceRecord();
            RecordFactory recordFactory=new RecordFactory(databaseConfig);
            Record record=recordFactory.get(IntranetDeviceRecord.class);
            String[] onemes = info.split("@");
            for (int i = 0;i<onemes.length;i++){
                System.out.println("onemes");
                int j;
                String[] oinfo = onemes[i].split("\\*");
//                for (j=0;j<6;j++){
//                    System.out.println(oinfo[j]);
//                }
                record.addRecord(oinfo[0],oinfo[1],oinfo[2],oinfo[4],oinfo[3],oinfo[5]);
            }
//            is.close();
//            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "success";
    }

    @GetMapping("/getintranetdeviceInfo")
    public List<intranetdeviceInfo> getintranetdeviceInfo() throws SQLException{
        List<intranetdeviceInfo> list=new ArrayList<>();
        String sql="select * from IntranetDeviceTable";
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        IntranetDeviceQuery IntranetDeviceQuery=new IntranetDeviceQuery(connection);
        list=IntranetDeviceQuery.queryRecord(sql);
        connection.close();
        System.out.println(sql);
        return list;
    }

    @GetMapping("/getintranetdeviceInfo_upnp")
    public List<intranetdeviceInfo> getintranetdeviceInfo_upnp() throws SQLException{
        List<intranetdeviceInfo> list=new ArrayList<>();
        String sql="select * from IntranetDeviceTable_UPNP";
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        IntranetDeviceQuery IntranetDeviceQuery=new IntranetDeviceQuery(connection);
        list=IntranetDeviceQuery.queryRecord(sql);
        connection.close();
        System.out.println(sql);
        return list;
    }

    @GetMapping("/searchintranetdevicevul")
    public List<intranetdeviceInfo> getdata(String intranetname, String IP, String device, String vendor,String devicetype) throws SQLException {
        List<intranetdeviceInfo> list=new ArrayList<>();

        String sql="select * from IntranetDeviceTable where ";
        if(!intranetname.equals(""))sql=sql+"intranetname='"+intranetname+"' ";
        if(!IP.equals("")){
            if(!intranetname.equals("")) sql=sql+"and IP='"+IP+"' ";
            else sql=sql+"IP='"+IP+"' ";
        }
        if(!devicetype.equals("")){
            if(!intranetname.equals("")||!IP.equals("")) sql=sql+"and devicetype='"+devicetype+"' ";
            else sql=sql+"devicetype='"+devicetype+"' ";
        }
        if(!device.equals("")){
            if(!intranetname.equals("")||!IP.equals("")||!devicetype.equals("")) sql=sql+"and device='"+device+"' ";
            else sql=sql+"device='"+device+"' ";
        }
        if(!vendor.equals("")){
            if(!intranetname.equals("")||!IP.equals("")||!devicetype.equals("")||!device.equals(""))
                sql=sql+"and vendor='"+vendor+"'";
            else
                sql=sql+"vendor='"+vendor+"'";
        }
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        java.sql.Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        IntranetDeviceQuery IntranetDeviceQuery=new IntranetDeviceQuery(connection);
        list=IntranetDeviceQuery.queryRecord(sql);
        connection.close();
        return list;
    }

    @GetMapping("/searchintranetdevicevul_upnp")
    public List<intranetdeviceInfo> getdata_upnp(String intranetname, String IP, String device, String vendor,String devicetype) throws SQLException {
        List<intranetdeviceInfo> list=new ArrayList<>();

        String sql="select * from IntranetDeviceTable_UPNP where ";
        if(!intranetname.equals(""))sql=sql+"intranetname='"+intranetname+"' ";
        if(!IP.equals("")){
            if(!intranetname.equals("")) sql=sql+"and IP='"+IP+"' ";
            else sql=sql+"IP='"+IP+"' ";
        }
        if(!devicetype.equals("")){
            if(!intranetname.equals("")||!IP.equals("")) sql=sql+"and devicetype='"+devicetype+"' ";
            else sql=sql+"devicetype='"+devicetype+"' ";
        }
        if(!device.equals("")){
            if(!intranetname.equals("")||!IP.equals("")||!devicetype.equals("")) sql=sql+"and device='"+device+"' ";
            else sql=sql+"device='"+device+"' ";
        }
        if(!vendor.equals("")){
            if(!intranetname.equals("")||!IP.equals("")||!devicetype.equals("")||!device.equals(""))
                sql=sql+"and vendor='"+vendor+"'";
            else
                sql=sql+"vendor='"+vendor+"'";
        }
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        java.sql.Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        IntranetDeviceQuery IntranetDeviceQuery=new IntranetDeviceQuery(connection);
        list=IntranetDeviceQuery.queryRecord(sql);
        connection.close();
        return list;
    }
}
