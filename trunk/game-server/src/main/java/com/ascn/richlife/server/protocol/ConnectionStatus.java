package com.ascn.richlife.server.protocol;

/**
 * 连接状态
 * Created by zhangpengxiang on 17/6/5.
 */
public class ConnectionStatus {

    /**
     * 在线
     */
    public static final int ON_LINE = 1;

    /**
     * 匹配对列中
     */
    public static final int MATCH_THE_QUEUE = 2;

    /**
     * 房间中
     */
    public static final int IN_THE_ROOM = 3;

    /**
     * 选择角色
     */
    public static final int CHOOSE_ROLE = 4;

    /**
     * 游戏初始化
     */
    public static final int IN_THE_GAME = 5;

    /**
     * 游戏进行中
     */
    public static final int IN_THE_GAMEING = 6;

}
