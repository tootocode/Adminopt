package com.example.demo.controller;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    ERROR(-1 ,"服务端错误"),

    SUCCESS(0, "成功"),

    File_NOT_EXIST(13 , "文件为空"),

    ;

    Integer code;

    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    String getDesc(){
        return desc;
    }
    Integer getCode(){
        return code;
    }
}