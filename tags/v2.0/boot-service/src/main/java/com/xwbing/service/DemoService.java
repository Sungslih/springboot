package com.xwbing.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DemoService {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();// 有序
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(5);
        list.add(4);
        list.add(6);
        System.out.println("filter:" + list.stream().filter(o1 -> o1 > 3 && o1 < 8).collect(Collectors.toList()));
        list.removeIf(o1 -> o1 > 3 && o1 < 8);
        System.out.println(list);
    }
}
