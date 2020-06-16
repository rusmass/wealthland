package com.ascn.richlife.service.test;

import com.ascn.richlife.model.login.User;
import com.ascn.richlife.service.login.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangpengxiang on 2017/6/8.
 */
public class UserServiceTest {
    private static ApplicationContext context;

    private UserService userService = (UserService) getBean("userService");

    static {
        context = new ClassPathXmlApplicationContext("spring-config.xml");
    }

    public Object getBean(String name) {
        return context.getBean(name);
    }


}
