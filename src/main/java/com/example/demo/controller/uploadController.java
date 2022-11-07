package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/file")

public class uploadController {

    @PostMapping(value="/upload", headers="Content-type=multipart/form-data")
    public ResponseVo upload(@RequestParam(value = "file") MultipartFile file) throws IOException, InterruptedException {

        System.out.println("有文件上传请求进来了");
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (file != null) {
                //String fileName = file.getOriginalFilename();
                String fileName = "seeds_file";
                if (StringUtils.isNotBlank(fileName)) {
                    java.io.File outFile = new java.io.File("/6tree/"+fileName);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            }
            else {
                return ResponseVo.error(ResponseEnum.File_NOT_EXIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVo.error(ResponseEnum.ERROR);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        String cmd1="/6tree/6tree -G -in-b4 seeds_file -out-tree tree_folder ";
        String cmd2="/6tree/6tree -R -in-tree tree_folder -out-res result_folder ";
        String cmd3="zmap --probe-module=icmp6_echoscan --ipv6-target-file=targets.txt --output-file=result.txt --ipv6-source-ip=2001:----::----:1002 --bandwidth=10M --cooldown-time=4 ";
        String cmd4="/6tree/6tree -R -in-tree tree_hex -out-res result_hex ";
        Process p= null;
        try {
            p = Runtime.getRuntime().exec(cmd1);
            System.out.println(cmd1);

            p = Runtime.getRuntime().exec(cmd2);
            System.out.println(cmd1);

            p = Runtime.getRuntime().exec(cmd3);
            System.out.println(cmd1);

            p = Runtime.getRuntime().exec(cmd4);
            System.out.println(cmd1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseVo.success(ResponseEnum.SUCCESS);
    }
}