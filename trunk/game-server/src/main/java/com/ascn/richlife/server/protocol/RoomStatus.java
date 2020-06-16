package com.ascn.richlife.server.protocol;

/**
 * 房间状态
 *
 * Created by Administrator on 2017/8/8 0008.
 */
public class RoomStatus {

    /**
     * 在房间中
     */
    public static final int IN_ROOM = 0;

    /**
     * 在选择角色中
     */
    public static final int IN_CHOOSEROLE = 1;

    /**
     * 在游戏中
     */
    public static final int IN_GAMEING = 2;

    /**
     * 不存在房间
     */
    public static final int NO_ROOM = -1;

}
