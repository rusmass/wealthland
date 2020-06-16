package com.ascn.richlife.server.core;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 获取配置文件
 *
 * Created by zhangpengxiang on 17/3/14.
 */
public class BeanFactory {

    private static Logger logger = Logger.getLogger(BeanFactory.class);

    private static ApplicationContext context;

    public static void init() {
        logger.info("开始加载spring....");
        if (context == null) {
            context = new ClassPathXmlApplicationContext("spring-config.xml");
        }
        logger.info("spring加载完毕....");
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }
}
