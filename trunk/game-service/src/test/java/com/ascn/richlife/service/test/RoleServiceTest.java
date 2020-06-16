package com.ascn.richlife.service.test;

import com.ascn.richlife.service.role.RoleService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * Created by zhangpengxiang on 2017/6/28.
 */
public class RoleServiceTest {

    private static ApplicationContext context;

    private RoleService roleService = (RoleService) getBean("roleService");


    static {
        context = new ClassPathXmlApplicationContext("spring-config.xml");
    }

    public Object getBean(String name) {
        return context.getBean(name);
    }

    @Test
    public void test(){
        HashMap<String,Integer> hs = new HashMap<>();
        TreeMap<String,Integer> treeMap = new TreeMap<String,Integer>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    @Test
    public void test1(){
        HashMap<Integer,String> hs = new HashMap<>();
        LinkedList<Map.Entry<Integer,String>> linkedList = new LinkedList<Map.Entry<Integer, String>>(hs.entrySet());
        Collections.sort(linkedList, new Comparator<Map.Entry<Integer, String>>() {
            @Override
            public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for(Map.Entry<Integer,String> entry:linkedList){
            hs.put(entry.getKey(),entry.getValue());
        }

    }

    @Test
    public void tesr(){
        System.err.println(roleService.getRoleInfo(100001));
    }
}
