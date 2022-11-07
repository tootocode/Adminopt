package com.example.demo.controller;

import com.example.demo.database.Query.*;
import com.example.demo.database.Record.DatabaseConfig;
import com.example.demo.database.Record.DeviceVulRecord;
import com.example.demo.database.Record.Record;
import com.example.demo.database.Record.RecordFactory;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spire.doc.*;
import com.spire.doc.documents.*;
import com.spire.doc.fields.TextRange;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class genetatelogController {

    @Autowired
    private DatabaseConfig databaseConfig;

    @GetMapping("/hellolog")
    public String hello() {
        System.out.println("hellolog");
        return "success";
    }

    @GetMapping("/generatelog")
    public String generatelog() throws SQLException {
//        List<ipdevicetypeInfo> list = new ArrayList<>();
//        List<exploittaskInfo> listexploit = new ArrayList<>();
//        //List<ALLVulInfo> list1 = new ArrayList<>();
//        String sql = "select IP,Vendor,DeviceType from IOTMark";
//        //System.out.println(databaseConfig.getMysqlip());
//        String mysqlPath = "jdbc:mysql://" + databaseConfig.getMysqlip() + "/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
//        //String mysqlPath="jdbc:mysql://101.76.243.58/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
//        Connection connection = DriverManager.getConnection(mysqlPath, "root", "123456");
//        generatelogQuery generatelogQuery = new generatelogQuery(connection);
//        list = generatelogQuery.queryRecord(sql);
//        connection.close();
//        String[] header = {"IP地址", "设备类型", "制造商/使用协议", "TLS/SSL漏洞","软件漏洞"};
//        //String[][] data = {new String[]{"27.223.66.6:502", "Modbus", "null", "null"}};//添加表格
//        //System.out.println(header.length);
//        String[][] data = new String[10000][header.length];//添加表
//        List<vulnerability> list5=new ArrayList<>();
//
//        try {
//            List<devicetaskInfo> list15=new ArrayList<>();
//            List<vulnerability> list55=new ArrayList<>();
//            sql="select * from IOTMark";
//            mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
//            connection= DriverManager.getConnection(mysqlPath,"root","123456");
//            devicemarktaskQuery devicemarktaskQuery=new devicemarktaskQuery(connection);
//            list15=devicemarktaskQuery.queryRecord(sql);
//            System.out.println(list15.size());
//            System.out.println(sql);
//            for (int i=0;i<list15.size();i++) {
//                List<vulnerability> list2=new ArrayList<>();
//                if (list15.get(i).getDevice().equals("")){
//                    list15.get(i).setDevice("*-*");
//                }
//                sql = "select * from Vulinfo where devicetype like '%"+list15.get(i).getDevice()+ "%'";
//                mysqlPath = "jdbc:mysql://" + databaseConfig.getMysqlip() + "/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
//                connection = DriverManager.getConnection(mysqlPath, "root", "123456");
//                vulnerabilityQuery vulnerabilityQuery = new vulnerabilityQuery(connection);
//                list2 = vulnerabilityQuery.queryRecord(sql);
//                connection.close();
//                for (int j = 0; j < list2.size(); j++) {
//                    list2.get(j).setTaskname(list15.get(i).getTaskname());
//                    list2.get(j).setIp(list15.get(i).getTarget());
//                    list2.get(j).setTaskdate(list15.get(i).getPostdate());
//                    if (list2.get(j).getDevicetype().contains(" ")){
//                        list2.get(j).setDevicetype(list2.get(j).getDevicetype().split(" ")[0]);
//                    }
//                    list2.get(j).setTitle(list2.get(j).getTitle().replaceAll("[^\\u4e00-\\u9fa5]", ""));
//                }
//                if (list2.size()>0){
//                    for (int p=0;p<list2.size();p++){
//                        if (list15.get(i).getVendor().toLowerCase().equals(list2.get(p).getVendors().toLowerCase())&&list15.get(i).getDevice().toLowerCase().equals(list2.get(p).getDevicetype().toLowerCase())) {
//                            list55.add(list2.get(p));
//                        }
//                    }
//                }
//                List<exploittaskInfo> listex=new ArrayList<>();
//                sql="select * from ExploitResult where IP='"+list15.get(i).getTarget()+"'";
//                mysqlPath="jdbc:mysql://"+databaseConfig.getMysqlip()+"/DeviceMark?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
//                connection= DriverManager.getConnection(mysqlPath,"root","123456");
//                exploittaskInfoQuery exploittaskInfoQuery=new exploittaskInfoQuery(connection);
//                listex=exploittaskInfoQuery.queryRecord(sql);
//                connection.close();
//                if (listex.size()>0){
//                    for (int p=0;p<listex.size();p++){
//                        vulnerability vv = new vulnerability();
//                        vv.setTaskname(list15.get(i).getTaskname());
//                        vv.setIp(list15.get(i).getTarget());
//                        if (list15.get(i).getDevice().equals("*-*")){
//                            vv.setDevicetype(list15.get(i).getDevicetype());
//                        }
//                        else{
//                            vv.setDevicetype(list15.get(i).getDevice());
//                        }
//                        vv.setVendors(list15.get(i).getVendor());
//                        vv.setTitle(listex.get(p).getExplain());
//                        vv.setTaskdate(listex.get(p).getPostdate());
//                        if (!listex.get(p).getExplain().equals("")){
//                            list55.add(vv);
//                        }
//                    }
//                }
//            }
//            for (int p = 0; p < list55.size(); p++) {
//                vulnerability vul = list55.get(p);
//                //System.out.println(vul.getDevicetype() + " " + vul.getDescription());
//                for (int q = p+1; q < list55.size(); q++){
//                    vulnerability vul1 = list55.get(q);
//                    if ((vul.getDevicetype().equals(vul1.getDevicetype())&&vul.getTitle().equals(vul1.getTitle()))||(vul.getDevicetype().equals(vul1.getDevicetype())&&vul.getTitle().contains(vul1.getTitle()))){
//                        list55.remove(list55.get(q));
//                        q--;
//                    }
//                    else if (vul.getDevicetype().equals(vul1.getDevicetype())&&!vul.getTitle().equals(vul1.getTitle())){
//                        list55.get(p).setTitle(list55.get(p).getTitle()+","+list55.get(q).getTitle());
//                        list55.remove(list55.get(q));
//                        q--;
//                    }
//                }
//            }
//            RecordFactory recordFactory=new RecordFactory(databaseConfig);
//            Record record=recordFactory.get(DeviceVulRecord.class);
//            for (int j = 0; j < list55.size(); j++) {
//                vulnerability vul = list55.get(j);
//                record.addRecord(vul.getTaskname(),vul.getIp(),vul.getDevicetype(),vul.getVendors(),vul.getTaskdate(),vul.getTitle(),vul.getHanlvl(),vul.getDescription());
//            }
//            recordFactory.getConnection().close();
//        }catch (Exception e){
//            System.out.println(e);
//        }
//
//        int[] tlsvulnumint = new int[10000];
//        int datanum = 0;
//        String IP = "";
//        String Devicetype = "";
//        String Vendor = "";
//        for (ipdevicetypeInfo s : list)  {
//            IP = s.getIP();
//            Devicetype = s.getDevicetype();
//            Vendor = s.getVendor();
//            //System.out.println(s.getIP() + "     " + s.getVendor()+" "+s.getDevicetype());
//            sql = "select * from ALLVulTable where host='"+s.getIP()+"'";
//            connection = DriverManager.getConnection(mysqlPath, "root", "123456");
//            ALLVulQuery ALLVulQuery = new ALLVulQuery(connection);
//            ALLVulInfo all = ALLVulQuery.queryRecord(sql);
//            connection.close();
//            int tlsvulnum = 0;
//            String mes = "";
//            if (all.getBeast()==1){
//                mes = mes + "beast\n";
//                tlsvulnum++;
//            }
//            if (all.getLucky13()==1){
//                mes = mes + "lucky13\n";
//                tlsvulnum++;
//            }
//            if (all.getCss()==1){
//                mes = mes + "css\n";
//                tlsvulnum++;
//            }
//            if (all.getRobot()==1){
//                mes = mes + "robot\n";
//                tlsvulnum++;
//            }
//            if (all.getPoodle()==1){
//                mes = mes + "poodle\n";
//                tlsvulnum++;
//            }
//            if (all.getCrime()==1){
//                mes = mes + "crime\n";
//                tlsvulnum++;
//            }
//            if (all.getBreach()==1){
//                mes = mes + "breach\n";
//                tlsvulnum++;
//            }
//            if (all.getFreak()==1){
//                mes = mes + "freak\n";
//                tlsvulnum++;
//            }
//            if (all.getLogjam()==1){
//                mes = mes + "logjam\n";
//                tlsvulnum++;
//            }
//            if (all.getRc4()==1){
//                mes = mes + "rc4\n";
//                tlsvulnum++;
//            }
//            if (all.getSweet32()==1){
//                mes = mes + "sweet32\n";
//                tlsvulnum++;
//            }
//            if (all.getHeartbleed()==1){
//                mes = mes + "heartbleed\n";
//                tlsvulnum++;
//            }
//            if (all.getTicketbleed()==1){
//                mes = mes + "ticketbleed";
//                tlsvulnum++;
//            }
//            String eti = "";
//            int el = 0;
//            sql = "select * from DeviceVulTable where IP='"+s.getIP()+"'";
//            //System.out.println(sql);
//            List<devicevulinfo> list1=new ArrayList<>();
//            connection = DriverManager.getConnection(mysqlPath, "root", "123456");
//            devicevulQuery dvq = new devicevulQuery(connection);
//            list1 = dvq.queryRecord(sql);
//            connection.close();
//            if (list1.size()>0){
//                String[] title = list1.get(0).getTitle().split(",");
//                for (String ss:title){
//                    eti = eti + ss + "\n";
//                    el++;
//                }
//            }
//            /*exploittaskInfoQuery etIQuery = new exploittaskInfoQuery(connection);
//            listexploit =  etIQuery.queryRecord(sql);
//            connection.close();
//            if (listexploit.size()>0){
//                if (listexploit.size()==1){
//                    exploittaskInfo etI = listexploit.get(0);
//                    eti = etI.getExplain();
//                    //System.out.println(eti);
//                    if (eti.contains("成功")) {
//                        eti = eti.split(",")[1];
//                        el++;
//                    }
//                }
//                else if (listexploit.size()>1){
//                    for (exploittaskInfo etI : listexploit){
//                        //eti = etI.getExplain();
//                        //System.out.println(etI.getTarget()+etI.getExplain()+"**");
//                        if (etI.getExplain().contains("成功")) {
//                            eti = eti + etI.getExplain().split(",")[1] + "\n";
//                            el++;
//                        }
//                    }
//                }
//                System.out.println(eti);
//            }*/
//            if (tlsvulnum>0||el>0){
//                if (tlsvulnum!=0&&el==0){
//                    tlsvulnumint[datanum] = tlsvulnum;
//                }
//                if (tlsvulnum==0&&el!=0){
//                    tlsvulnumint[datanum] = el;
//                }
//                else {
//                    tlsvulnumint[datanum] = tlsvulnum>el?tlsvulnum:el;
//                }
//
//                data[datanum]= new String[]{IP, Devicetype, Vendor,mes,eti};
//                datanum++;
//            }
//            /*if (datanum>=5){
//                break;
//            }*/
//        }
//
//        /*tlsvulnumint[datanum] = 1;
//        data[datanum]=new String[]{"58.56.24.174:502", "PLC", "Modbus","","权限绕过漏洞"};
//        datanum++;*/
//
//        Document document = new Document();//添加一个section
//        Section section = document.addSection();//数据
//        Table table = section.addTable(true);//设置表格的行数和列数
//        table.resetCells(datanum+1, header.length);//定义行数和列数，行数+1为包括头header
//        TableRow row = table.getRows().get(0);
//        row.isHeader(true);
//        row.setHeight(30);
//        row.setHeightType(TableRowHeightType.Exactly);
//        row.getRowFormat().setBackColor(Color.gray);
//        for (int i = 0; i < header.length; i++) {
//            row.getCells().get(i).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
//            Paragraph p = row.getCells().get(i).addParagraph();
//            p.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
//            TextRange range1 = p.appendText(header[i]);
//            range1.getCharacterFormat().setFontName("Arial");
//            range1.getCharacterFormat().setFontSize(12f);
//            range1.getCharacterFormat().setBold(true);
//        }//添加数据到剩余行
//        for (int r = 0; r <datanum; r++) {  //每行存入数据
//            TableRow dataRow = table.getRows().get(r + 1);
//            dataRow.setHeight(13*tlsvulnumint[r]);
//            dataRow.setHeightType(TableRowHeightType.Exactly);
//            dataRow.getRowFormat().setBackColor(Color.white);
//            for (int c = 0; c < data[r].length; c++) {
//                dataRow.getCells().get(c).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
//                TextRange range2 = dataRow.getCells().get(c).addParagraph().appendText(data[r][c]);
//                range2.getCharacterFormat().setFontName("Arial");
//                range2.getCharacterFormat().setFontSize(10f);
//            }
//        }//设置单元格背景颜色
//        for (int j = 1; j < table.getRows().getCount(); j++) {
//            if (j % 2 == 0) {
//                TableRow row2 = table.getRows().get(j);
//                for (int f = 0; f < row2.getCells().getCount(); f++) {
//                    row2.getCells().get(f).getCellFormat().setBackColor(new Color(173, 216, 230));
//                }
//            }
//        }//保存文档
//
//        document.saveToFile("扫描报告.docx");
//        //System.out.println(list);
//        System.out.println("finish");
        String cmd = "python3.8 /produce_word.py";
        Process p= null;
        try {
            p = Runtime.getRuntime().exec(cmd);
            System.out.println(cmd);
            int status = p.waitFor();

        } catch (IOException | InterruptedException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        p.destroy();
        return "success";
    }

    public static void main(String[] argv) throws SQLException {
        genetatelogController ge = new genetatelogController();
        ge.generatelog();
    }
}
