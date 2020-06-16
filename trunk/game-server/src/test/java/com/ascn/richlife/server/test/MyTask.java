package com.ascn.richlife.server.test;

/**
 * Created by zhangpengxiang on 2017/6/29.
 */
public class MyTask implements Runnable {

    private String roomId;

    public String getRoomId() {
        return roomId;
    }

    public MyTask setRoomId(String roomId) {
        this.roomId = roomId;
        return this;
    }

    @Override
    public void run() {

        System.err.println("解散房间号："+roomId);

    }

}
