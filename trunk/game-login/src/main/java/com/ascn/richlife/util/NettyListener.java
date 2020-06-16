package com.ascn.richlife.util;

import com.ascn.richlife.server.core.GameInit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 启动游戏逻辑服务器的监听
 */
public class NettyListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new GameInit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
