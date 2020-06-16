package com.ascn.richlife.server.handler;

import com.ascn.richlife.model.login.Player;
import com.ascn.richlife.server.group.ClientConnectionManager;
import com.ascn.richlife.server.group.RoomManager;
import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.util.BuildResponseMessage;
import com.ascn.richlife.service.login.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 聊天管理
 *
 * Created by Administrator on 2017/8/8 0008.
 */
@Component("chatHandler")
public class ChatHandler {

    @Autowired
    PlayerService playerService;

    /**
     * 构建响应信息
     *
     * @param message
     * @return
     * @throws Exception
     */

    private NettyMessage build(NettyMessage message) throws Exception {
        //获取玩家信息
        Player player = playerService.getPlayerInfo(message.getHeader().getPlayerId());

        Map<String, Object> data = new HashMap();

        data.put("headImg", player.getAvatar());

        data.put("gender", player.getGender());

        data.put("name", player.getName());

        data.put("msg", message.getBody().get("msg"));

        //获取当前服务器时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        data.put("sendTime", sdf.format(date));

        //创建响应信息
        NettyMessage nettyMessage = BuildResponseMessage.build(message.getHeader().getType(), message.getHeader().getPlayerId(), 0, data);
        return nettyMessage;
    }

    /**
     * 大厅广播信息
     *
     * @param message
     * @throws Exception
     */
    public void radioCast(NettyMessage message) throws Exception {

        //发送给所有玩家广播信息
        NettyMessage nettyMessage = build(message);
        ClientConnectionManager.sendMessage(nettyMessage);
    }

    /**
     * 玩家游戏房间内聊天
     *
     * @param message
     */

    public void roomChat(NettyMessage message) throws Exception {

        //获取房间ID
        String roomId = (String) message.getBody().get("roomId");

        //获取房间集合
        List<String> players = RoomManager.getPlayers(roomId);

        //发送信息到同一房间玩家
        NettyMessage nettyMessage = build(message);
        ClientConnectionManager.sendMessage(players, nettyMessage);
    }
}
