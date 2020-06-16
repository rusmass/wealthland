package com.ascn.richlife.server.core;

import com.ascn.richlife.server.handler.GameDataHandler;
import com.ascn.richlife.server.turntable.Turntable;

/**
 * 游戏初始化
 *
 * Created by zhangpengxiang on 17/3/14.
 */
public class GameInit {

    public GameInit() throws Exception {
        //加载spring配置文件
        BeanFactory.init();

        //加载游戏数据
        GameDataHandler gameDataHandler = (GameDataHandler) BeanFactory.getBean("gameDataHandler");

        gameDataHandler.init();

        Turntable.init();

        //启动netty服务器
        NettyServer.init();
    }
   /* public static void main(String[] args) throws Exception {

        //加载spring配置文件
        BeanFactory.init();

        //加载游戏数据
        GameDataHandler gameDataHandler = (GameDataHandler) BeanFactory.getBean("gameDataHandler");

        gameDataHandler.init();

        Turntable.init();

        //启动netty服务器
        NettyServer.init();
    }*/

}
