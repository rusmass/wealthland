package com.ascn.richlife.server.task;

import com.ascn.richlife.server.group.ClientConnectionManager;
import com.ascn.richlife.server.group.RoomManager;

import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.util.BuildResponseMessage;
import org.apache.log4j.Logger;

/**
 * Created by zhangpengxiang on 2017/6/30.
 */
public class ChooseRoleCountdownTask implements Runnable {

    Logger logger = Logger.getLogger(ChooseRoleCountdownTask.class);

    private String roomId;

    public ChooseRoleCountdownTask(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public void run() {

        NettyMessage message = BuildResponseMessage.build(100000, "", 0, null);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

    }



}
