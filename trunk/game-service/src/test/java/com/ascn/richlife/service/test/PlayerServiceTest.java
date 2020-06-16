package com.ascn.richlife.service.test;

import com.ascn.richlife.service.login.PlayerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangpengxiang on 2017/6/8.
 */
public class PlayerServiceTest {

    private static ApplicationContext context;

    private PlayerService playerService = (PlayerService) getBean("playerService");


    static {
        context = new ClassPathXmlApplicationContext("spring-config.xml");
    }

    public Object getBean(String name) {
        return context.getBean(name);
    }



}
