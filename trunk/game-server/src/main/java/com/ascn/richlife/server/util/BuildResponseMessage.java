package com.ascn.richlife.server.util;

import com.ascn.richlife.server.message.Header;
import com.ascn.richlife.server.message.NettyMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * 构建消息体
 *
 * Created by zhangpengxiang on 17/4/8.
 */
public class BuildResponseMessage {

    /**
     * 构建
     *
     * @param type
     * @param playerId
     * @param status
     * @param data
     * @return
     */
    public static NettyMessage build(int type, String playerId, int status, Map<String, Object> data) {

        NettyMessage message = new NettyMessage();

        Header header = new Header();

        header.setType(type);

        if (playerId.length()>0){
            header.setPlayerId(playerId);
        }

        message.setHeader(header);

        Map<String, Object> body = new HashMap<String, Object>();

        body.put("status", status);

        if (data != null) {
            body.put("data", data);
        }

        message.setBody(body);

        return message;

    }

}
