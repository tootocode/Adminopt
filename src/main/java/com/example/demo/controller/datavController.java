package com.example.demo.controller;

import com.example.demo.database.Query.*;
import com.example.demo.database.Record.DatabaseConfig;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@RestController
@CrossOrigin
public class datavController {
    @Autowired
    private DatabaseConfig databaseConfig;

    @GetMapping("/datav/digitalFlop")
    public List<datavdevicenum> getdatavdevicenum() throws SQLException {
        List<datavdevicenum> list=new ArrayList<>();
        String sql="select DeviceType,count(IOTMark.IP) as num from IOTMark group by DeviceType order by num desc";
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        datavDevicenumQuery datavDevicenumQuery=new datavDevicenumQuery(connection);
        list=datavDevicenumQuery.queryRecord(sql);
        connection.close();
        return list;
    }

    @GetMapping("/datav/vendorRatio")
    public List<vendorratio> getdatavVendorratio() throws SQLException {
        List<vendorratio> list=new ArrayList<>();
        List<datavdevicenum> list1=new ArrayList<>();
        String sql1="select DeviceType,count(IOTMark.IP) as num from IOTMark group by DeviceType order by num desc";
        String sql2="select Vendor,count(IOTMark.IP) as num from IOTMark where DeviceType='";
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        datavDevicenumQuery datavDevicenumQuery=new datavDevicenumQuery(connection);
        list1=datavDevicenumQuery.queryRecord(sql1);
        for(datavdevicenum datavdevicenum:list1){
            String tempsql=sql2+datavdevicenum.getDeviceType()+"' group by Vendor order by num desc";
            // System.out.println(tempsql);
            vendorRatioQuery vendorRatioQuery=new vendorRatioQuery(connection);
            list.add(vendorRatioQuery.queryRecord(tempsql,datavdevicenum.getDeviceType(),datavdevicenum.getNum()));
        }
        connection.close();
        //System.out.println(list);
        for (int i=0;i<list.size();i++){
            if (list.get(i).getVendor().equals("")){
                list.get(i).setVendor("其他");
            }
        }
        return list;
    }

    @GetMapping("/datav/countbugs")
    public List<listInfo> getBugRank() throws SQLException {
        List<listInfo> list=new ArrayList<>();
        List<String> tlsbugs=new ArrayList<>();
        List<bugInfo> temp=new ArrayList<>();
        tlsbugs.add("heartbleed");
        tlsbugs.add("css");
        tlsbugs.add("ticketbleed");
        tlsbugs.add("robot");
        tlsbugs.add("secure_renego");
        tlsbugs.add("crime");
        tlsbugs.add("breach");
        tlsbugs.add("sweet32");
        tlsbugs.add("freak");
        tlsbugs.add("logjam");
        tlsbugs.add("beast");
        tlsbugs.add("lucky13");
        tlsbugs.add("rc4");
        tlsbugs.add("poodle");
        tlsbugs.add("learn_long_session_live");
        tlsbugs.add("openssl_aes_ni");
        String sql="select count(*) as number from ALLVulTable where ";
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        countNumberQuery countNumberQuery=new countNumberQuery(connection);
        for(String bug :tlsbugs){
            String tempsql=sql+bug+"='1'";
            int t=countNumberQuery.queryRecord(tempsql);
            bugInfo bugInfo=new bugInfo();
            bugInfo.setName(bug);
            bugInfo.setNum(t);
            temp.add(bugInfo);
        }

        Collections.sort(temp, new Comparator<bugInfo>() {
            @Override
            public int compare(bugInfo o1, bugInfo o2) {
                return o2.getNum()-o1.getNum();
            }
        });

        int i=0;

        while(i<temp.size()&&i<10){
            listInfo listInfo=new listInfo();
            listInfo.setRank(i+1);
            listInfo.setName(temp.get(i).getName());
            listInfo.setNum(temp.get(i).getNum());
            list.add(listInfo);
            i++;
        }
        //System.out.println(list);
        connection.close();
        return list;
    }

    @GetMapping("/datav/scrollboard")
    public List<devicetaskInfo> getInitlist() throws SQLException {
        List<devicetaskInfo> result=new ArrayList<>();
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        scrollboardQuery scrollboardQuery=new scrollboardQuery(connection);
        result=scrollboardQuery.queryRecord();
        connection.close();
        return result;
    }

    @GetMapping("/datav/rosechart")
    public List<datavdevicenum> getRoschartData() throws SQLException {
        List<datavdevicenum> result=new ArrayList<>();
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        datavdeviceQuery datavdeviceQuery=new datavdeviceQuery(connection);
        result=datavdeviceQuery.queryRecord();
        connection.close();
        return result;
    }

    @GetMapping("/datav/waterlevel")
    public datavwaterlevel getWaterLevelData() throws SQLException {
        int result=0;
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        numQuery numQuery=new numQuery(connection);
        int num1=numQuery.queryRecord("select count(distinct(IP)) as num from ScanedIP");
        int num2=numQuery.queryRecord("select count(distinct(IP)) as num from IOTMark");
        //System.out.println(num1);
        //System.out.println(num2);
        datavwaterlevel datavwaterlevel=new datavwaterlevel();
        datavwaterlevel.setNum(num2);
        result=num2*100/num1;
        if (result==0)
            result=1;
        datavwaterlevel.setRatio(result);
        //System.out.println(num1+" "+num2+" "+result+" "+num2*100/num1);
        connection.close();
        return datavwaterlevel;
    }

    @GetMapping("/datav/chinamap")
    public List<deviceaddressnum> getChinaMap() throws SQLException{
        List<deviceaddressnum> list=new ArrayList<>();
        List<String> province=new ArrayList<>();
        List<deviceaddressnum> temp=new ArrayList<>();
        province.add("山东");province.add("黑龙江");
        province.add("北京");province.add("江苏");
        province.add("河北");province.add("安徽");
        province.add("内蒙古");province.add("江西");
        province.add("吉林");province.add("河南");
        province.add("上海");province.add("湖南");
        province.add("浙江");province.add("广西");
        province.add("福建");province.add("重庆");
        province.add("湖北");province.add("贵州");
        province.add("广东");province.add("西藏");
        province.add("海南");province.add("甘肃");
        province.add("四川");province.add("宁夏");
        province.add("云南");province.add("香港");
        province.add("陕西");province.add("台湾");
        province.add("青海");province.add("新疆");
        province.add("澳门");province.add("天津");
        province.add("山西");province.add("辽宁");
        String sql="select count(distinct(IP)) as number from IOTaddress where ";
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        deviceaddressQuery deviceaddressQuery=new deviceaddressQuery(connection);
        countNumberQuery countNumberQuery=new countNumberQuery(connection);
        for(String aprovince :province){
            String tempsql=sql+"province"+"='"+aprovince+"'";
            int t=countNumberQuery.queryRecord(tempsql);
            deviceaddressnum deviceaddressnum=new deviceaddressnum();
            deviceaddressnum.setName(aprovince);
            deviceaddressnum.setNum(t);
            temp.add(deviceaddressnum);
        }
        Collections.sort(temp, new Comparator<deviceaddressnum>() {
            @Override
            public int compare(deviceaddressnum o1, deviceaddressnum o2) {
                return o2.getNum()-o1.getNum();
            }
        });

        int i=0;

        while(i<temp.size()){
            deviceaddressnum deviceaddressnum=new deviceaddressnum();
            deviceaddressnum.setRank(i+1);
            deviceaddressnum.setName(temp.get(i).getName());
            deviceaddressnum.setNum(temp.get(i).getNum());
            list.add(deviceaddressnum);
            i++;
        }
        //System.out.println(list);
        connection.close();
        return list;
    }

    @GetMapping("/datav/chinamapnum")
    public List<deviceaddressnum> getChinaMapNum() throws SQLException{
        List<deviceaddressnum> list=new ArrayList<>();
        List<String> province=new ArrayList<>();
        List<deviceaddressnum> temp=new ArrayList<>();
        province.add("山东");province.add("黑龙江");
        province.add("北京");province.add("江苏");
        province.add("河北");province.add("安徽");
        province.add("内蒙古");province.add("江西");
        province.add("吉林");province.add("河南");
        province.add("上海");province.add("湖南");
        province.add("浙江");province.add("广西");
        province.add("福建");province.add("重庆");
        province.add("湖北");province.add("贵州");
        province.add("广东");province.add("西藏");
        province.add("海南");province.add("甘肃");
        province.add("四川");province.add("宁夏");
        province.add("云南");province.add("香港");
        province.add("陕西");province.add("台湾");
        province.add("青海");province.add("新疆");
        province.add("澳门");province.add("天津");
        province.add("山西");province.add("辽宁");
        String sql="select count(distinct(IP)) as number from IOTaddress where ";
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        deviceaddressQuery deviceaddressQuery=new deviceaddressQuery(connection);
        countNumberQuery countNumberQuery=new countNumberQuery(connection);
        for(String aprovince :province){
            String tempsql=sql+"province"+"='"+aprovince+"'";
            int t=countNumberQuery.queryRecord(tempsql);
            deviceaddressnum deviceaddressnum=new deviceaddressnum();
            deviceaddressnum.setName(aprovince);
            deviceaddressnum.setNum(t);
            temp.add(deviceaddressnum);
        }
        Collections.sort(temp, new Comparator<deviceaddressnum>() {
            @Override
            public int compare(deviceaddressnum o1, deviceaddressnum o2) {
                return o2.getNum()-o1.getNum();
            }
        });

        int i=0;

        while(i<temp.size()&&i<10){
            deviceaddressnum deviceaddressnum=new deviceaddressnum();
            deviceaddressnum.setRank(i+1);
            deviceaddressnum.setName(temp.get(i).getName());
            deviceaddressnum.setNum(temp.get(i).getNum());
            list.add(deviceaddressnum);
            i++;
        }
        //System.out.println(list);
        connection.close();
        return list;
    }

    @GetMapping("/datav/sdmapnum")
    public List<deviceaddressnum> getSdMapNum() throws SQLException{
        List<deviceaddressnum> list=new ArrayList<>();
        List<String> province=new ArrayList<>();
        List<deviceaddressnum> temp=new ArrayList<>();
        province.add("青岛市");
        province.add("淄博市");province.add("枣庄市");
        province.add("东营市");province.add("烟台市");
        province.add("潍坊市");province.add("济宁市");
        province.add("泰安市");province.add("威海市");
        province.add("日照市");province.add("滨州市");
        province.add("德州市");province.add("聊城市");
        province.add("临沂市");province.add("菏泽市");
        String sql="select count(distinct(IP)) as number from IOTaddress where ";
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        deviceaddressQuery deviceaddressQuery=new deviceaddressQuery(connection);
        countNumberQuery countNumberQuery=new countNumberQuery(connection);
        for(String aprovince :province){
            String tempsql=sql+"city"+"='"+aprovince+"'";
            int t=countNumberQuery.queryRecord(tempsql);
            deviceaddressnum deviceaddressnum=new deviceaddressnum();
            deviceaddressnum.setName(aprovince);
            deviceaddressnum.setNum(t);
            temp.add(deviceaddressnum);

        }
        String tempsql=sql+"city"+"='"+"济南市"+"'" +" or "+ "city"+"='"+"莱芜市"+"'";
        int t=countNumberQuery.queryRecord(tempsql);
        deviceaddressnum deviceaddressnum=new deviceaddressnum();
        deviceaddressnum.setName("济南市");
        deviceaddressnum.setNum(t);
        temp.add(deviceaddressnum);

        Collections.sort(temp, new Comparator<deviceaddressnum>() {
            @Override
            public int compare(deviceaddressnum o1, deviceaddressnum o2) {
                return o2.getNum()-o1.getNum();
            }
        });

        int i=0;

        while(i<temp.size()&&i<10){
            deviceaddressnum=new deviceaddressnum();
            deviceaddressnum.setRank(i+1);
            deviceaddressnum.setName(temp.get(i).getName());
            deviceaddressnum.setNum(temp.get(i).getNum());
            list.add(deviceaddressnum);
            i++;
        }
        //System.out.println(list);
        connection.close();
        return list;
    }

    @GetMapping("/datav/sdpercent")
    public datavwaterlevel getSdPercent() throws SQLException {
        int result=0;
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        numQuery numQuery=new numQuery(connection);
        int num1=numQuery.queryRecord("select count(distinct(IP)) as num from IOTaddress");
        int num2=numQuery.queryRecord("select count(distinct(IP)) as num from IOTaddress where province='山东'");
        //System.out.println(num1);
        //System.out.println(num2);
        datavwaterlevel datavwaterlevel=new datavwaterlevel();
        datavwaterlevel.setNum(num2);
        result=num2*100/num1;
        if (result==0)
            result=1;
        datavwaterlevel.setRatio(result);
        // System.out.println(num1+" "+num2+" "+result+" "+num2*100/num1);
        connection.close();
        return datavwaterlevel;
    }

    @GetMapping("/datav/sdmap")
    public List<deviceaddressnum> getSdMap() throws SQLException{
        List<deviceaddressnum> list=new ArrayList<>();
        List<String> province=new ArrayList<>();
        List<deviceaddressnum> temp=new ArrayList<>();
        province.add("青岛市");
        province.add("淄博市");province.add("枣庄市");
        province.add("东营市");province.add("烟台市");
        province.add("潍坊市");province.add("济宁市");
        province.add("泰安市");province.add("威海市");
        province.add("日照市");province.add("滨州市");
        province.add("德州市");province.add("聊城市");
        province.add("临沂市");province.add("菏泽市");
        String sql="select count(distinct(IP)) as number from IOTaddress where ";
        String mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        Connection connection= DriverManager.getConnection(mysqlPath,"root","123456");
        deviceaddressQuery deviceaddressQuery=new deviceaddressQuery(connection);
        countNumberQuery countNumberQuery=new countNumberQuery(connection);
        for(String aprovince :province){
            String tempsql=sql+"city"+"='"+aprovince+"'";
            int t=countNumberQuery.queryRecord(tempsql);
            deviceaddressnum deviceaddressnum=new deviceaddressnum();
            deviceaddressnum.setName(aprovince);
            deviceaddressnum.setNum(t);
            temp.add(deviceaddressnum);
        }

        String tempsql=sql+"city"+"='"+"济南市"+"'" +" or "+ "city"+"='"+"莱芜市"+"'";
        int t=countNumberQuery.queryRecord(tempsql);
        deviceaddressnum deviceaddressnum=new deviceaddressnum();
        deviceaddressnum.setName("济南市");
        deviceaddressnum.setNum(t);
        temp.add(deviceaddressnum);
//        deviceaddressnum=new deviceaddressnum();
//        deviceaddressnum.setName("莱芜市");
//        deviceaddressnum.setNum(t);
//        temp.add(deviceaddressnum);

        Collections.sort(temp, new Comparator<deviceaddressnum>() {
            @Override
            public int compare(deviceaddressnum o1, deviceaddressnum o2) {
                return o2.getNum()-o1.getNum();
            }
        });

        int i=0;

        while(i<temp.size()){
            deviceaddressnum=new deviceaddressnum();
            deviceaddressnum.setRank(i+1);
            deviceaddressnum.setName(temp.get(i).getName());
            deviceaddressnum.setNum(temp.get(i).getNum());
            list.add(deviceaddressnum);
            i++;
        }
        //System.out.println(list);
        connection.close();
        return list;
    }

}
