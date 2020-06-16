package com.ascn.richlife.server.protocol;

/**
 * 游戏状态
 *
 * Created by Administrator on 2017/9/20 0020.
 * @author zhenhb
 */
public class GameStatus {

    /**
     * 游戏创建
     */
    public static final int CRATE_GAME = 0;

    /**
     * 游戏初始化
     */
    public static final int INIT_GAME = 1;

    /**
     * 游戏已开始
     */
    public static final int START_GAMEING = 2;

    /**
     * 游戏结束
     */
    public static final int NOT_GAME = -1;
}
