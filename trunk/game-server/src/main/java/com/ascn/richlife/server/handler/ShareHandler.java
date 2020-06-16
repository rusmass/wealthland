package com.ascn.richlife.server.handler;

import com.ascn.richlife.model.Share;
import com.ascn.richlife.server.group.ClientConnectionManager;
import com.ascn.richlife.server.message.MessageStatus;
import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.protocol.ProtoIds;
import com.ascn.richlife.server.util.BuildResponseMessage;
import com.ascn.richlife.service.share.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 游戏分享工具类
 *
 * Created by Administrator on 2017/8/8 0008.
 */
@Component("shareHandler")
public class ShareHandler {

    @Autowired
    ShareService shareService;

    public void findGame(String playerId) {

        Map<String,Object> data = buildMap(shareService.findGame(playerId));
        NettyMessage message = BuildResponseMessage.build(ProtoIds.SHARE_GAME,playerId, MessageStatus.SUCCESS,data);

        //发送响应数据
        ClientConnectionManager.sendMessage(playerId,message);

    }

    public void findRoom(String playerId) {

        Map<String,Object> data = buildMap(shareService.findRoom(playerId));
        NettyMessage message = BuildResponseMessage.build(ProtoIds.SHARE_ROOM,playerId,MessageStatus.SUCCESS,data);
        //发送响应数据
        ClientConnectionManager.sendMessage(playerId,message);
    }


    public void findDreamBoard(String playerId){
        Map<String,Object> data = buildMap(shareService.findDreamBoard());
        NettyMessage message = BuildResponseMessage.build(ProtoIds.SHARE_DREAMBOARD,playerId,MessageStatus.SUCCESS,data);
        //发送相应数据

        ClientConnectionManager.sendMessage(playerId,message);
    }

    //封装响应数据
    private Map<String,Object>  buildMap(Share share){
        Map<String,Object> data = new HashMap<String, Object>();
        data.put("title",share.getTitle());
        data.put("txt",share.getTxt());
        data.put("address",share.getAddress());
        data.put("weburl",share.getWeburl());
        return data;
    }
}
