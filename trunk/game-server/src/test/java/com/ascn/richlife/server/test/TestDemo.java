package com.ascn.richlife.server.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangpengxiang on 2017/6/20.
 */
public class TestDemo {

    private static Map<String,Map<String,Boolean>> one = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        Map<String,Boolean> s = new HashMap<>();

        s.put("111",false);

        one.put("1",s);

        System.err.println(one.get("1").toString());

        Map<String,Boolean> a = one.get("1");

        a.put("111",true);

        System.err.println(one.get("1").toString());
    }

}
