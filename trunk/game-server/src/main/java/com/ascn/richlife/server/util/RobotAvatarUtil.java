package com.ascn.richlife.server.util;


/**
 * 生成机器人随机头像工具类
 *
 * Created by Administrator on 2018/5/17 0017.
 */
public class RobotAvatarUtil {

    /**
     * 随机生成机器人头像
     *
     * @return
     */
    public static String getRobotAvatar(){
        String url= "http://118.144.74.82:8080/game/upload/";
        String[] avatar = {"1514191279257.png","1514191279257.png","1514190986284.png","1514191279257.png"};
        return url+avatar[(int) (Math.random()*4)];
    }
}
