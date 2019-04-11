package com.xwbing.demo;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 项目名称: boot-module-pro
 * 创建时间: 2018/1/23 14:45
 * 作者: xiangwb
 * 说明: 测试用
 */
@Log
public class Ademo {
    public static void main(String[] args) {
        List<JSONObject> list=new ArrayList<>();
        Map<String, List<JSONObject>> aa = list.stream().collect(Collectors.groupingBy(obj -> obj.getString("aa")));
        System.out.println("aa");

    }
}
